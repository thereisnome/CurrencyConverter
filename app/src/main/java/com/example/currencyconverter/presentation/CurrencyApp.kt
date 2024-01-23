package com.example.currencyconverter.presentation

import android.app.Application
import com.example.currencyconverter.di.DaggerAppComponent

class CurrencyApp : Application() {

    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }

}