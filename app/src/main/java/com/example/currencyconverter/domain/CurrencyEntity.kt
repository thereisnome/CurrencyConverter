package com.example.currencyconverter.domain

data class CurrencyEntity(
    val id: String,
    val charCode: String,
    val name: String,
    val value: Double
)