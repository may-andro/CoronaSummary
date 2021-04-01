package com.mayandro.coronasummary.ui.home.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mayandro.coronasummary.R
import com.mayandro.coronasummary.databinding.LayoutGlobalSummaryBinding
import com.mayandro.coronasummary.utils.AutoUpdatableAdapter
import com.mayandro.coronasummary.utils.ChartUtils
import kotlin.properties.Delegates

class SummaryAdapter : RecyclerView.Adapter<SummaryAdapter.ViewHolder>(), AutoUpdatableAdapter {

    var dataSet: List<DashboardSummaryModel> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n ->
            o == n
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_global_summary, parent,
            false
        )

        return ViewHolder(LayoutGlobalSummaryBinding.bind(view))
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.render(dataSet[position])
    }

    inner class ViewHolder(
        private val binding: LayoutGlobalSummaryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            ChartUtils.setUpChart(binding.lineChartTop)
            ChartUtils.setUpChart(binding.lineChartBottom)
            binding.root.setOnClickListener {
            }
        }

        fun render(summaryModel: DashboardSummaryModel) {
            binding.textViewTopLabel.text = summaryModel.label1
            binding.textViewTopCount.text = summaryModel.value1

            binding.textViewBottomLabel.text = summaryModel.label2
            binding.textViewBottomCount.text = summaryModel.value2

            binding.circularProgressBar.progress = summaryModel.percentage
            binding.textPercentage.text = "${summaryModel.percentage}%"

            ChartUtils.fillChart(
                binding.lineChartTop,
                summaryModel.chartData1,
                summaryModel.label1,
                R.color.graphRed,
                R.drawable.red_graph_gradient
            )
            ChartUtils.fillChart(
                binding.lineChartBottom,
                summaryModel.chartData2,
                summaryModel.label2,
                R.color.graphGreen,
                R.drawable.green_graph_gradient
            )
        }
    }

}