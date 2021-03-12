package com.mayandro.utility.di

import android.content.Context
import android.content.SharedPreferences
import com.mayandro.utility.preference.PreferencesHelper
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val utilityModule = module{
    single { provideSharedPreference(androidApplication()) }
    single { PreferencesHelper(get()) }
}

private fun provideSharedPreference(context: Context): SharedPreferences {
    return context.getSharedPreferences("corona_prefs", Context.MODE_PRIVATE)
}