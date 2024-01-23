package com.example.currencyconverter.presentation

import com.example.currencyconverter.domain.CurrencyEntity

sealed class State

data object Loading: State()

data object Error: State()

class Success(val result: List<CurrencyEntity>): State()