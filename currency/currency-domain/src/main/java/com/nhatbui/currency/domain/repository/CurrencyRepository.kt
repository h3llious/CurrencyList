package com.nhatbui.currency.domain.repository

import com.nhatbui.currency.domain.model.CurrencyDomainModel
import com.nhatbui.currency.domain.model.CurrencyRequestDomainModel
import com.nhatbui.currency.domain.model.CurrencyTypeDomainModel
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun insertCurrencies()
    suspend fun deleteCurrencies()
    suspend fun getCurrencies(request: CurrencyRequestDomainModel): Flow<List<CurrencyDomainModel>>
}
