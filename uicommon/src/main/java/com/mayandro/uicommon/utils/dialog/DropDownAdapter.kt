package com.mayandro.uicommon.utils.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mayandro.uicommon.R
import com.mayandro.uicommon.databinding.ItemBottomSheetListBinding

class DropDownAdapter(private val list: List<String>,private val selectedItem: Int?)
    : RecyclerView.Adapter<DropDownAdapter.ViewHolder>() {

    var selectionListener: OnItemSelectedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_bottom_sheet_list, parent,
            false
        )
        return ViewHolder(ItemBottomSheetListBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.render(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemSelectedListener {
        fun onItemSelected(position: Int)
    }

    inner class ViewHolder(private val binding: ItemBottomSheetListBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                selectionListener?.onItemSelected(adapterPosition)
            }
        }

        fun render(data: String) {
            binding.textValue.text = data
            val color = if(selectedItem == adapterPosition) {
                R.color.colorPrimary
            } else {
                R.color.black
            }
            binding.textValue.setTextColor(ContextCompat.getColor(binding.root.context, color))
        }
    }
}