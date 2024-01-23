package com.example.currencyconverter.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.currencyconverter.data.database.models.CurrencyDBModel

@Database(entities = [CurrencyDBModel::class], version = 1, exportSchema = false)
abstract class CurrencyDatabase: RoomDatabase() {
    companion object {

        private var db: CurrencyDatabase? = null
        private const val DB_NAME = "main.db"
        private val LOCK = Any()

        fun getInstance(context: Context): CurrencyDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(context, CurrencyDatabase::class.java, DB_NAME).build()
                db = instance
                return instance
            }
        }
    }

    abstract fun currencyDao(): CurrencyDao
}