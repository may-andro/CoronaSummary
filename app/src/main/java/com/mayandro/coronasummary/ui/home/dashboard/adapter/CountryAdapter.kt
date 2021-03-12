package com.mayandro.coronasummary.ui.home.dashboard.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.mayandro.coronasummary.R
import com.mayandro.coronasummary.databinding.ItemCountryBinding
import com.mayandro.coronasummary.utils.AutoUpdatableAdapter
import java.util.*
import kotlin.properties.Delegates

class CountryAdapter() : RecyclerView.Adapter<CountryAdapter.ViewHolder>(), AutoUpdatableAdapter {

    var dataSet: List<DashboardCountryModel> by Delegates.observable(emptyList()) {
            _, old, new ->
        autoNotify(old, new ) { o, n ->
            o == n
        }
    }

    private var onItemClickListener: OnItemClickListener? = null

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(
            R.layout.item_country, viewGroup,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(dataSet[position])
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(item: DashboardCountryModel, position: Int)
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemCountryBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                onItemClickListener?.onItemClick(dataSet[adapterPosition], adapterPosition)
            }
        }

        fun bind(data: DashboardCountryModel) {
            //binding.imageChart.setColorFilter(data.backgroundColor, android.graphics.PorterDuff.Mode.SRC_IN);
            binding.textViewName.text = data.countryCode.capitalize(Locale.getDefault())
            binding.textViewCount.text = data.totalCase
            binding.flagView.countryCode = data.countryCode
            binding.cardView.setCardBackgroundColor(data.backgroundColor)
        }
    }
}