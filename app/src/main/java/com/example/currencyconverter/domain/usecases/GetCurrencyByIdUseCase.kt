package com.example.currencyconverter.domain.usecases

import com.example.currencyconverter.domain.CurrencyEntity
import com.example.currencyconverter.domain.CurrencyRepo
import javax.inject.Inject

class GetCurrencyByIdUseCase @Inject constructor(private val currencyRepo: CurrencyRepo) {

    suspend operator fun invoke(id: String, date: String): CurrencyEntity{
        return currencyRepo.getCurrencyById(id, date)
    }
}