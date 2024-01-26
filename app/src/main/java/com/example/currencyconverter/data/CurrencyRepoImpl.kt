package com.example.currencyconverter.data

import com.example.currencyconverter.data.database.CurrencyDao
import com.example.currencyconverter.data.network.ApiService
import com.example.currencyconverter.domain.CurrencyEntity
import com.example.currencyconverter.domain.CurrencyRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrencyRepoImpl @Inject constructor(
    private val currencyDao: CurrencyDao,
    private val apiService: ApiService,
    private val mapper: Mapper,
) : CurrencyRepo {

    override suspend fun loadData() {
        val currencyListDto = apiService.getCurrencyList()
        currencyListDto.valute.forEach {
            val currencyDb = mapper.mapCurrencyDTOtoDB(it.value, currencyListDto)
            currencyDao.insertCurrency(currencyDb)
        }
    }

    override fun getCurrencyList(date: String): Flow<List<CurrencyEntity>> {
        return currencyDao.getCurrencyList(date).map { list -> list.map { mapper.mapCurrencyDBtoEntity(it) } }
    }

    override suspend fun getCurrencyById(id: String, date: String): CurrencyEntity {
        return mapper.mapCurrencyDBtoEntity(currencyDao.getCurrencyById(id, date))
    }
}