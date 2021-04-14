package com.mayandro.coronasummary.ui.home.dashboard

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.mayandro.coronasummary.R
import com.mayandro.coronasummary.databinding.FragmentDashboardBinding
import com.mayandro.coronasummary.ui.home.dashboard.adapter.CountryAdapter
import com.mayandro.coronasummary.ui.home.dashboard.adapter.DashboardCountryModel
import com.mayandro.coronasummary.ui.home.dashboard.adapter.DashboardSummaryModel
import com.mayandro.coronasummary.ui.home.dashboard.adapter.SummaryAdapter
import com.mayandro.uicommon.base.BaseFragment
import com.mayandro.uicommon.utils.showShortToast
import com.mayandro.utility.network.NetworkStatus
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {
    private val dashboardViewModel: DashboardViewModel by viewModel()

    private var isPortrait: Boolean = true

    private val countryAdapter: CountryAdapter by lazy { CountryAdapter() }

    override fun getViewBinding(): FragmentDashboardBinding =
        FragmentDashboardBinding.inflate(layoutInflater)

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

        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.colorSecondary)

        setUpCountryRecyclerView()
        setUpPagerView()
        setUpReadMore()

        dashboardViewModel.successCountryListState.observe(viewLifecycleOwner) {
            (binding.recyclerViewCountries.adapter as CountryAdapter).dataSet = it
        }

        dashboardViewModel.successDashboardSummaryState.observe(viewLifecycleOwner) {
            (binding.viewPagerSummary.adapter as SummaryAdapter).dataSet = it
        }

        dashboardViewModel.errorState.observe(viewLifecycleOwner) {
            showErrorDialog(it)
        }
    }

    //Setup View
    private fun setUpPagerView() {
        binding.viewPagerSummary.apply {
            isUserInputEnabled = false
            adapter = SummaryAdapter()
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPagerSummary) { tab, position ->
            val title = when (position) {
                0 -> getString(R.string.recovered)
                1 -> getString(R.string.death)
                2 -> getString(R.string.active_cases)
                else -> "Unknown"
            }
            tab.text = title
        }.attach()
    }

    private fun setUpCountryRecyclerView() {
        binding.recyclerViewCountries.apply {
            setHasFixedSize(true)
            val orientation = if (isPortrait) {
                LinearLayoutManager.HORIZONTAL
            } else {
                LinearLayoutManager.VERTICAL
            }
            layoutManager = LinearLayoutManager(
                requireContext(),
                orientation,
                false
            )

            adapter = countryAdapter
        }

        countryAdapter.onItemClick = {
            findNavController().navigate(
                R.id.action_dashboardFragment_to_countryDetailFragment,
                bundleOf(
                    getString(R.string.arg_country_slug) to it.slug,
                    getString(R.string.arg_country_total_cases) to it.totalCase,
                    getString(R.string.arg_country_background) to it.backgroundColor
                )
            )
        }
    }

    private fun setUpReadMore() {
        binding.textReadMore.setOnClickListener {
            showUpdateDialog()
        }

        binding.imageInfo.setOnClickListener {
            showUpdateDialog()
        }
    }

    //Dialog
    private fun showUpdateDialog() {
        alertDialog.showAlertMessage(
            context = requireContext(),
            title = "Coming soon...",
            message = "This feature is coming soon. Stay tuned.",
            positiveButton = "Dismiss"
        )
    }

    private fun showErrorDialog(error: String) {
        showAlertDialog(
            title = "Api Error",
            message = error,
            positiveButton = "Dismiss"
        )
    }
}