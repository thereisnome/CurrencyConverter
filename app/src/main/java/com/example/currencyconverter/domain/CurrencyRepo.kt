package com.example.currencyconverter.domain

import kotlinx.coroutines.flow.Flow

interface CurrencyRepo {

    suspend fun loadData()

    fun getCurrencyList(date: String): Flow<List<CurrencyEntity>>

    fun getCurrencyListByCharCode(charCode: String, date: String): Flow<List<CurrencyEntity>>

    suspend fun getCurrencyById(id: String, date: String): CurrencyEntity
}