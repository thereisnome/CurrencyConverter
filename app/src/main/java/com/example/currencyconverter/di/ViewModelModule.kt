package com.example.currencyconverter.di

import androidx.lifecycle.ViewModel
import com.example.currencyconverter.presentation.viewModels.CurrencyDetailsViewModel
import com.example.currencyconverter.presentation.viewModels.CurrencyListViewModel
import com.example.currencyconverter.presentation.viewModels.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(value = CurrencyListViewModel::class)
    fun bindCurrencyListViewModel(viewModel: CurrencyListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(value = CurrencyDetailsViewModel::class)
    fun bindCurrencyDetailsViewModel(viewModel: CurrencyDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(value = MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}