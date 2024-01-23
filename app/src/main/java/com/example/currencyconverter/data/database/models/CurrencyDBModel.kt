package com.example.currencyconverter.data.database.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_list")
data class CurrencyDBModel(
    @PrimaryKey
    @Embedded
    val compositeKey: CompositePrimaryKey,
    val charCode: String,
    val name: String,
    val value: Double
)