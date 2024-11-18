package com.nhatbui.currency.domain.repository

import com.nhatbui.currency.domain.model.CurrencyDomainModel
import com.nhatbui.currency.domain.model.CurrencyRequestDomainModel
import com.nhatbui.currency.domain.model.CurrencyTypeDomainModel
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun updateFilterPreference(type: CurrencyTypeDomainModel)
    suspend fun getFilterPreference(): Flow<CurrencyTypeDomainModel>
}
