package com.mayandro.coronasummary.ui.home

import android.os.Bundle
import com.mayandro.coronasummary.databinding.ActivityHomeBinding
import com.mayandro.coronasummary.ui.base.BaseActivity
import com.mayandro.utility.extensions.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity: BaseActivity<ActivityHomeBinding>(), HomeViewInteractor {

    override fun getViewBinding() = ActivityHomeBinding.inflate(layoutInflater)

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.viewInteractor = this
    }

    override fun showAlert(message: String) {
        this.showToast(message)
    }
}