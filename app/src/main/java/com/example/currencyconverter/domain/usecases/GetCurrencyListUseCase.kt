package com.example.currencyconverter.domain.usecases

import com.example.currencyconverter.domain.CurrencyEntity
import com.example.currencyconverter.domain.CurrencyRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrencyListUseCase @Inject constructor(private val currencyRepo: CurrencyRepo) {

    operator fun invoke(date: String): Flow<List<CurrencyEntity>> {
        return currencyRepo.getCurrencyList(date)
    }
}