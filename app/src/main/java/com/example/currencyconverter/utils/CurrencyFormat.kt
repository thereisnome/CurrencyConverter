package com.example.currencyconverter.utils

import java.text.NumberFormat
import java.util.Locale

fun formatCurrency(value: Double): String {
    return NumberFormat.getCurrencyInstance(Locale("ru", "RU")).format(value)
}

fun formatCurrencyWithoutSign(value: Double): String {
    return NumberFormat.getCurrencyInstance(Locale("ru", "RU")).format(value).replace( "₽", "")
}
