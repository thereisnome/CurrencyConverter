package com.example.currencyconverter.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.currencyconverter.databinding.ActivityMainBinding
import com.example.currencyconverter.presentation.viewModels.MainViewModel
import com.example.currencyconverter.presentation.viewModels.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as CurrencyApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.loadData()
    }
}