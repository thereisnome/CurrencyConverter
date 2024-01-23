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
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MainModule {

    @Binds
    fun bindCurrencyRepo(impl: CurrencyRepoImpl): CurrencyRepo

    companion object{
        @Singleton
        @Provides
        fun provideApiService():ApiService{
            return ApiFactory.apiService
        }

        @Singleton
        @Provides
        fun provideCurrencyDao(application: Application): CurrencyDao{
            return CurrencyDatabase.getInstance(application).currencyDao()
        }
    }
}