package com.example.currencyconverter.data

import com.example.currencyconverter.data.database.models.CompositePrimaryKey
import com.example.currencyconverter.data.database.models.CurrencyDBModel
import com.example.currencyconverter.data.network.models.CurrencyDTO
import com.example.currencyconverter.data.network.models.CurrencyListDTO
import com.example.currencyconverter.domain.CurrencyEntity
import javax.inject.Inject

class Mapper @Inject constructor() {

    fun mapCurrencyDTOtoDB(currencyDto: CurrencyDTO, currencyListDTO: CurrencyListDTO) = CurrencyDBModel(
        compositeKey = CompositePrimaryKey(currencyDto.id, currencyListDTO.date),
        charCode = currencyDto.charCode,
        name = currencyDto.name,
        value = currencyDto.value,
        previous = currencyDto.previous,
        nominal = currencyDto.nominal
    )

    fun mapCurrencyDBtoEntity(db: CurrencyDBModel) = CurrencyEntity(
        id = db.compositeKey.id,
        charCode = db.charCode,
        name = db.name,
        value = db.value,
        previous = db.previous,
        nominal = db.nominal
    )
}