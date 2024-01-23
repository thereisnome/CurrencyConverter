package com.example.currencyconverter.domain.usecases

import com.example.currencyconverter.domain.CurrencyRepo
import javax.inject.Inject

class LoadDataUseCase @Inject constructor(private val currencyRepo: CurrencyRepo) {

    suspend operator fun invoke(){
        currencyRepo.loadData()
    }
}