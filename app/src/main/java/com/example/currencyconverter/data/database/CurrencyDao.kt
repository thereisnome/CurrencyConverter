package com.example.currencyconverter.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyconverter.data.database.models.CurrencyDBModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCurrency(currencyDbModel: CurrencyDBModel)

    @Query("SELECT * FROM currency_list WHERE substr(date, 1, 10) = :date")
    fun getCurrencyList(date: String): Flow<List<CurrencyDBModel>>

    @Query("SELECT * FROM currency_list WHERE substr(date, 1, 10) = :date AND charCode LIKE '%' || :charCode || '%'")
    fun getCurrencyListByCharCode(charCode: String, date: String): Flow<List<CurrencyDBModel>>

    @Query("SELECT * FROM currency_list WHERE id = :id AND substr(date, 1, 10) = :date")
    suspend fun getCurrencyById(id: String, date: String): CurrencyDBModel
}