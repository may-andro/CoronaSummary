package com.mayandro.feature_country_summary.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.mayandro.feature_country_summary.R
import com.mayandro.feature_country_summary.databinding.FragmentCountryDetailBinding
import com.mayandro.feature_country_summary.di.featureCountrySummaryUIModule
import com.mayandro.feature_country_summary.ui.adapter.CountryStatsAdapter
import com.mayandro.feature_country_summary.ui.model.StateSuccessModel
import com.mayandro.feature_country_summary.utils.TOGGLE_STATE_GRAPH
import com.mayandro.feature_country_summary.utils.TOGGLE_STATE_LIST
import com.mayandro.uicommon.base.BaseFragment
import com.mayandro.uicommon.utils.chart.ChartUtils
import com.mayandro.uicommon.utils.recyclerview.SpaceItemDecoration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import java.util.*
import com.mayandro.coronasummary.R as mainResource

class CountryDetailFragment: BaseFragment<FragmentCountryDetailBinding>() {

    private val countryDetailViewModel: CountryDetailViewModel by viewModel()

    lateinit var countrySlug: String

    private var isPortrait: Boolean = true

    private val countryStatsAdapter: CountryStatsAdapter by lazy { CountryStatsAdapter() }

    override fun getViewBinding(): FragmentCountryDetailBinding = FragmentCountryDetailBinding.inflate(
        layoutInflater
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(featureCountrySummaryUIModule)
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(featureCountrySummaryUIModule)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val orientation = resources.configuration.orientation
        isPortrait = orientation == Configuration.ORIENTATION_PORTRAIT
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt(getString(mainResource.string.arg_country_background))?.let {
            binding.motionLayout.setBackgroundColor(it)
            countryStatsAdapter.backgroundColor = it
        }

        arguments?.getString(getString(mainResource.string.arg_country_slug))?.let {
            countrySlug = it
        }

        arguments?.getString(getString(mainResource.string.arg_country_total_cases))?.let {
            binding.textViewLabel.text = getString(mainResource.string.total_cases)
            binding.textViewCount.text = it

        }

        ChartUtils.setUpChart(binding.lineChart)
        setUpTabView()
        setUpFilterImage()
        setUpRecyclerView()
        setUpRefreshViewImage()
        setBackButton()
        binding.tabLayout.setScrollPosition(countryDetailViewModel.selectedTab, 0f,true)

        countryDetailViewModel.errorState.observe(
            viewLifecycleOwner
        ) {
            showErrorDialog(it)
        }

        countryDetailViewModel.successState.observe(
            viewLifecycleOwner
        ) {
            if(binding.motionLayout.currentState == R.id.stateLoading) binding.motionLayout.transitionToEnd()
            val currentCountrySummary = it.last()
            binding.flagView.countryCode = currentCountrySummary.countryCode
            binding.textViewName.text = currentCountrySummary.country.capitalize(Locale.getDefault())

        }

        countryDetailViewModel.successUiData.observe(
            viewLifecycleOwner
        ) {
            setStateSuccessData(it)
        }

        if(savedInstanceState == null) countryDetailViewModel.getCountrySummary(countrySlug)
    }

    //View Setup
    private fun setUpTabView() {
        binding.tabLayout.apply {
            addTab(this.newTab().setText(getString(mainResource.string.recovered)))
            addTab(this.newTab().setText(getString(mainResource.string.death)))
            addTab(this.newTab().setText(getString(mainResource.string.active_cases)))

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    countryDetailViewModel.onTabSelection(tab?.position ?: 0)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(SpaceItemDecoration(20))
            adapter = countryStatsAdapter
        }
    }


    private fun setUpFilterImage() {
        binding.imageFilter.setOnClickListener {
            showTimeRangeDialog()
        }
    }

    private fun setUpRefreshViewImage() {
        binding.imageViewRefresh.setOnClickListener {
            countryDetailViewModel.getCountrySummary(
                country = countrySlug
            )
        }
    }

    private fun setBackButton() {
        binding.viewBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setImageState() {
        when(countryDetailViewModel.selectedToggleState) {
            TOGGLE_STATE_GRAPH -> {
                binding.imageViewType.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        mainResource.drawable.ic_baseline_view_list_24
                    )
                )
            }
            TOGGLE_STATE_LIST -> {
                binding.imageViewType.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        mainResource.drawable.ic_baseline_show_chart_24
                    )
                )
            }
        }
    }

    //State Management
    private fun setStateSuccessData(stateSuccessModel: StateSuccessModel) {
        binding.textViewDetailTitle.text = stateSuccessModel.detailLabel
        binding.textViewDetailValue.text = stateSuccessModel.detailValue

        countryStatsAdapter.tabPosition = binding.tabLayout.selectedTabPosition
        countryStatsAdapter.dataSet = stateSuccessModel.summaryList.reversed()
        countryStatsAdapter.notifyDataSetChanged()

        ChartUtils.fillChart(
            binding.lineChart,
            stateSuccessModel.chartDataModel,
            stateSuccessModel.detailLabel,
            mainResource.color.graphRed,
            mainResource.drawable.red_graph_gradient
        )
    }

    //Dialogs
    private fun showErrorDialog(message: String) {
        showAlertDialog(
            title = "Country List Data Error",
            message = message,
            positiveButton = "Dismiss",
            positiveButtonClickListener = {
                findNavController().popBackStack()
            }
        )
    }

    private fun showTimeRangeDialog() {
        lifecycleScope.launch {
            countryDetailViewModel.getTimeRangeOptionList()
                .flowOn(Dispatchers.Default)
                .collect { triple ->
                    alertDialog.showListBottomSheet(
                        context = requireContext(),
                        title = "Select the time range",
                        message = "Here are options for selecting the date range",
                        list = triple.second,
                        onItemSelected = {
                            countryDetailViewModel.onTimeRangeSelection(triple.first[it])
                            countryDetailViewModel.getCountrySummary(country = countrySlug)
                        },
                        selectedItem = triple.third
                    )
                }
        }
    }

    //Helper Method
    private fun toggleViewState(start: Int, end: Int) {
        binding.motionLayout.setTransition(start, end)
        binding.motionLayout.setTransitionDuration(1000)
        binding.motionLayout.transitionToEnd()
    }

    private fun toggleImageTypeIcon() {
        when(countryDetailViewModel.selectedToggleState) {
            TOGGLE_STATE_GRAPH -> {
                toggleViewState(R.id.stateCard, R.id.stateList)
                countryDetailViewModel.selectedToggleState = TOGGLE_STATE_LIST
                binding.imageViewType.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        mainResource.drawable.ic_baseline_show_chart_24
                    )
                )
            }
            TOGGLE_STATE_LIST -> {
                countryDetailViewModel.selectedToggleState = TOGGLE_STATE_GRAPH
                toggleViewState(R.id.stateList, R.id.stateCard)
                binding.imageViewType.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        mainResource.drawable.ic_baseline_view_list_24
                    )
                )
            }
        }
    }
}