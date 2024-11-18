package com.nhatbui.currencylist.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.nhatbui.currency.data.repository.SettingsRepositoryImpl
import com.nhatbui.currency.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "settings")

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun providesDataStore(@ApplicationContext context: Context) = context.dataStore

    @Provides
    fun providesSettingsRepository(
        dataStore: DataStore<Preferences>
    ): SettingsRepository = SettingsRepositoryImpl(dataStore)
}
