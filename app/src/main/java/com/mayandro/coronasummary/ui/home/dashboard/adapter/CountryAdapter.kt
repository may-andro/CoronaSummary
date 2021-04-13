package com.mayandro.coronasummary.ui.home.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mayandro.coronasummary.R
import com.mayandro.coronasummary.databinding.ItemCountryBinding
import com.mayandro.uicommon.utils.AutoUpdatableAdapter
import java.util.*
import kotlin.properties.Delegates

class CountryAdapter() : RecyclerView.Adapter<CountryAdapter.ViewHolder>(), AutoUpdatableAdapter {

    var dataSet: List<DashboardCountryModel> by Delegates.observable(emptyList()) {
            _, old, new ->
        autoNotify(old, new ) { o, n ->
            o == n
        }
    }

    var onItemClick: ((DashboardCountryModel) -> Unit)? = null

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

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemCountryBinding.bind(view)

        init {
            binding.cardView.setOnClickListener {
                onItemClick?.invoke(dataSet[adapterPosition])
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