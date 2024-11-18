package com.nhatbui.currency.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.nhatbui.currency.data.model.CurrencyPreferenceDataType
import com.nhatbui.currency.data.model.toDataType
import com.nhatbui.currency.data.model.toDomain
import com.nhatbui.currency.domain.model.CurrencyTypeDomainModel
import com.nhatbui.currency.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {
    override suspend fun updateFilterPreference(type: CurrencyTypeDomainModel) {
        dataStore.edit { preferences->
            preferences[TYPE_KEY] = type.toDataType().ordinal
        }
    }

    override suspend fun getFilterPreference(): Flow<CurrencyTypeDomainModel> {
        return dataStore.data.map { preferences->
            val typeOrdinal = preferences[TYPE_KEY]?: CurrencyPreferenceDataType.ALL.ordinal
            CurrencyPreferenceDataType.entries[typeOrdinal].toDomain()
        }
    }

    companion object {
        private val TYPE_KEY = intPreferencesKey("type")
    }
}
