package com.example.currencyconverter.di

import android.app.Application
import com.example.currencyconverter.data.CurrencyRepoImpl
import com.example.currencyconverter.data.database.CurrencyDao
import com.example.currencyconverter.data.database.CurrencyDatabase
import com.example.currencyconverter.data.network.ApiFactory
import com.example.currencyconverter.data.network.ApiService
import com.example.currencyconverter.domain.CurrencyRepo
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @AppScope
    fun bindCurrencyDao(impl: CurrencyRepoImpl): CurrencyRepo

    companion object{

        @Provides
        @AppScope
        fun provideCurrencyDao(
            application: Application
        ): CurrencyDao{
            return CurrencyDatabase.getInstance(application).currencyDao()
        }

        @Provides
        @AppScope
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}