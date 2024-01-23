package com.example.currencyconverter.data.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_list")
data class CurrencyListDBModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String,
    val valute: Map<String, CurrencyDBModel>
)