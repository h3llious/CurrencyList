package com.nhatbui.currency.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
data class CurrencyDataModel(
    @PrimaryKey val id: String,
    val name: String,
    val symbol: String,
    val code: String?
)
