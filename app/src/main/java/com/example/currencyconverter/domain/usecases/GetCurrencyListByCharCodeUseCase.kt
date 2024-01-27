package com.example.currencyconverter.domain.usecases

import com.example.currencyconverter.domain.CurrencyEntity
import com.example.currencyconverter.domain.CurrencyRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrencyListByCharCodeUseCase @Inject constructor(private val currencyRepo: CurrencyRepo) {

    operator fun invoke(charCode: String, date: String): Flow<List<CurrencyEntity>> {
        return currencyRepo.getCurrencyListByCharCode(charCode, date)
    }
}