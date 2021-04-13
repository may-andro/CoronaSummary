package com.mayandro.feature_country_summary.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mayandro.feature_country_summary.R
import com.mayandro.feature_country_summary.databinding.ItemCountryStatsBinding
import com.mayandro.feature_country_summary.utils.TAB_ACTIVE_CASE
import com.mayandro.feature_country_summary.utils.TAB_DEAD
import com.mayandro.feature_country_summary.utils.TAB_RECOVERED
import com.mayandro.remote.model.CountryStatsResponse
import com.mayandro.utility.dataandtime.getDate
import com.mayandro.utility.extensions.getFormattedString
import com.mayandro.coronasummary.R as mainResource

class CountryStatsAdapter() : RecyclerView.Adapter<CountryStatsAdapter.ViewHolder>() {

    var tabPosition: Int = 0
    var backgroundColor: Int = mainResource.color.colorPrimary
    var dataSet: List<CountryStatsResponse> = listOf()

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(
            R.layout.item_country_stats, viewGroup,
            false
        )
        return ViewHolder(ItemCountryStatsBinding.bind(view))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(dataSet[position])
    }

    inner class ViewHolder(private val binding: ItemCountryStatsBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.cardView.setCardBackgroundColor(backgroundColor)
        }

        fun bind(data: CountryStatsResponse) {
            binding.textViewDate.text = data.date.getDate("MMM dd YYYY", "yyyy-MM-dd'T'HH:mm:ss'Z'")

            val count = when(tabPosition) {
                TAB_ACTIVE_CASE -> data.active
                TAB_DEAD -> data.deaths
                TAB_RECOVERED -> data.recovered
                else -> 0
            }
            binding.textViewCount.text = count.getFormattedString()
        }
    }
}