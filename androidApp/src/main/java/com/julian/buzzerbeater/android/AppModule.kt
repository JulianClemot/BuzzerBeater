package com.julian.buzzerbeater.android

import com.julian.buzzerbeater.BluetoothHelper
import com.julian.buzzerbeater.android.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { BluetoothHelper(androidContext()) }

    viewModel { HomeViewModel(get()) }
}