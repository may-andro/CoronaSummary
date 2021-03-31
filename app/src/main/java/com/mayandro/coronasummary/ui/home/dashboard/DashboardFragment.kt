package com.mayandro.coronasummary.ui.home.dashboard

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.mayandro.coronasummary.R
import com.mayandro.coronasummary.databinding.FragmentDashboardBinding
import com.mayandro.coronasummary.ui.base.BaseFragment
import com.mayandro.coronasummary.ui.home.dashboard.adapter.CountryAdapter
import com.mayandro.coronasummary.ui.home.dashboard.adapter.SummaryAdapter
import com.mayandro.remote.model.SummaryResponse
import com.mayandro.utility.extensions.showToast
import com.mayandro.utility.network.NetworkStatus
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment<FragmentDashboardBinding>(), DashboardInteractor {
    private val dashboardViewModel: DashboardViewModel by viewModel()

    private var isPortrait: Boolean = true

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
        dashboardViewModel.viewInteractor = this

        dashboardViewModel.getCoronaSummary()

        setUpCountryRecyclerView()
        setUpPagerView()

        dashboardViewModel.summaryResponseLiveData.observe(viewLifecycleOwner) {
            handleUiState(it)
        }
    }

    private fun handleUiState(networkStatus: NetworkStatus<SummaryResponse>) {
        when (networkStatus) {
            is NetworkStatus.Loading -> {
                //showAlertDialog(title = "Loading Data", message = "Loading Content", positiveButton = "", isCancellable = false)
            }
            is NetworkStatus.Error -> {
                showAlertDialog(
                    title = "Error",
                    message = networkStatus.errorMessage?: "Error happened",
                    positiveButton = "Terminate App",
                    positiveButtonClickListener = {
                        requireActivity().finish()
                    },
                    negativeButton = "Retry",
                    negativeButtonClickListener = {
                        dashboardViewModel.getCoronaSummary()
                    },
                    isCancellable = false
                )
            }
            is NetworkStatus.Success -> {
                alertDialog.dismissDialog()
                showSuccessUiData(networkStatus.data)
            }
        }
    }

    private fun showSuccessUiData(summaryResponse: SummaryResponse?) {
        summaryResponse?.let {
            dashboardViewModel.getUiData(it) { pagerList, recyclerViewList ->
                (binding.viewPagerSummary.adapter as SummaryAdapter).dataSet = pagerList
                (binding.recyclerViewCountries.adapter as CountryAdapter).dataSet = recyclerViewList
            }
        }
    }

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

            adapter = CountryAdapter()
        }
    }

    override fun showAlert(message: String) {
        requireActivity().showToast(message)
    }

    private fun showAlertDialog(
        title: String,
        message: String,
        positiveButton: String,
        negativeButton: String = "",
        positiveButtonClickListener: ((View) -> Unit)? = null,
        negativeButtonClickListener: ((View) -> Unit)? = null,
        isCancellable: Boolean = true
    ) {
        alertDialog.showAlertMessage(
            requireContext(),
            title = title,
            message = message,
            positiveButton,
            negativeButton,
            positiveButtonClickListener,
            negativeButtonClickListener,
            isCancellable
        )
    }
}