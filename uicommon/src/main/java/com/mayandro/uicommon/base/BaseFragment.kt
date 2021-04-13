package com.mayandro.uicommon.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.mayandro.uicommon.utils.dialog.DialogUtils

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

    //Dialogs
    fun showAlertDialog(
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