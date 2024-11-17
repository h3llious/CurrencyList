package com.nhatbui.currency.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nhatbui.currency.data.model.CurrencyDataModel

const val DATABASE_NAME = "currency-db"

@Database(entities = [CurrencyDataModel::class], version = 1, exportSchema = false)
abstract class CurrencyDatabase: RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}
