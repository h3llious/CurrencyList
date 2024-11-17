package com.nhatbui.currency.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nhatbui.currency.data.model.CurrencyDataModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<CurrencyDataModel>)

    @Query("SELECT * FROM currency")
    fun getAll(): Flow<List<CurrencyDataModel>>

    @Query("DELETE FROM currency")
    suspend fun deleteAll(): Int
}
