package com.mayandro.coronasummary.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.mayandro.coronasummary.utils.DialogUtils

abstract class BaseFragment<B : ViewBinding>() : Fragment(){
    abstract fun getViewBinding(): B

    lateinit var binding: B

    val alertDialog: DialogUtils by lazy {
        DialogUtils()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding()
        return binding.root
    }

    fun handleLoadingDialog(flag: Boolean) {

    }
}