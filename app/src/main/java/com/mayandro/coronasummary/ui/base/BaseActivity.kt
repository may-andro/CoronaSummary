package com.mayandro.coronasummary.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.mayandro.coronasummary.utils.DialogUtils

abstract class BaseActivity<B : ViewBinding>() : AppCompatActivity() {
    abstract fun getViewBinding(): B

    private lateinit var binding: B

    val alertDialog: DialogUtils by lazy {
        DialogUtils()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
    }


    fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}