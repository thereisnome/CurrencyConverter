package com.example.currencyconverter.data.network

import com.example.currencyconverter.data.network.models.CurrencyListDTO
import retrofit2.http.GET

interface ApiService {

    @GET("daily_json.js")
    suspend fun getCurrencyList(): CurrencyListDTO
}