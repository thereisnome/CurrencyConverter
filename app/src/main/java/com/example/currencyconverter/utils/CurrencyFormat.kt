package com.example.currencyconverter.utils

import java.text.NumberFormat
import java.util.Locale

fun formatCurrency(value: Double): String {
    return NumberFormat.getCurrencyInstance(Locale("ru", "RU")).format(value)
}

fun formatCurrencyWithoutSign(value: Double): String {
    return NumberFormat.getCurrencyInstance(Locale("ru", "RU")).format(value).replace("₽", "")
}

fun formatDiff(value: Double): String {
    val formattedAmount = NumberFormat.getCurrencyInstance(Locale("ru", "RU")).format(value)
    return if (value>=0){
        "+$formattedAmount".replace(" руб.", "₽")
    } else formattedAmount.replace(" руб.", "₽")
}