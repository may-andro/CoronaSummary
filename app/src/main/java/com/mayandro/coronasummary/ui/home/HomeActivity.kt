package com.mayandro.coronasummary.ui.home

import android.os.Bundle
import com.mayandro.coronasummary.databinding.ActivityHomeBinding
import com.mayandro.coronasummary.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity: BaseActivity<ActivityHomeBinding>() {

    override fun getViewBinding() = ActivityHomeBinding.inflate(layoutInflater)

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}