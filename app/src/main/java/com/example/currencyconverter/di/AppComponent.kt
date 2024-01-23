package com.example.currencyconverter.di

import android.app.Application
import com.example.currencyconverter.presentation.fragments.CurrencyDetailsFragment
import com.example.currencyconverter.presentation.fragments.CurrencyListFragment
import com.example.currencyconverter.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [DataModule::class, ViewModelModule::class]
)
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: CurrencyListFragment)

    fun inject(fragment: CurrencyDetailsFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}