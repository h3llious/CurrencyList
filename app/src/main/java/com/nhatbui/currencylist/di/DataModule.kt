package com.nhatbui.currencylist.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nhatbui.common.data.AssetToDataMapper
import com.nhatbui.common.domain.CoroutineContextProvider
import com.nhatbui.currency.data.local.CurrencyDao
import com.nhatbui.currency.data.local.CurrencyDatabase
import com.nhatbui.currency.data.local.DATABASE_NAME
import com.nhatbui.currency.data.mapper.CurrencyDataModelToDomainMapper
import com.nhatbui.currency.data.mapper.CurrencySearchContentResolver
import com.nhatbui.currency.data.model.CurrencyDataModel
import com.nhatbui.currency.data.repository.CurrencyRepositoryImpl
import com.nhatbui.currency.domain.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun providesCurrencyDatabase(
        @ApplicationContext context: Context
    ): CurrencyDatabase =
        Room.databaseBuilder(context, CurrencyDatabase::class.java, DATABASE_NAME).build()

    @Provides
    @Singleton
    fun providesCurrencyDao(
        database: CurrencyDatabase
    ): CurrencyDao = database.currencyDao()

    @Provides
    fun providesAssetToCurrenciesMapper(
        @ApplicationContext context: Context,
        gson: Gson
    ): AssetToDataMapper<List<CurrencyDataModel>> = AssetToDataMapper(
        context = context,
        gson = gson,
        type = object : TypeToken<List<CurrencyDataModel>>() {}.type
    )

    @Provides
    fun providesCurrencyDataModelToDomainMapper() = CurrencyDataModelToDomainMapper()

    @Provides
    fun providesCurrencySearchContentResolver() = CurrencySearchContentResolver()

    @Provides
    fun providesCurrencyRepository(
        currencyDao: CurrencyDao,
        assetToDataMapper: AssetToDataMapper<List<CurrencyDataModel>>,
        currencyDataModelToDomainMapper: CurrencyDataModelToDomainMapper,
        currencySearchContentResolver: CurrencySearchContentResolver,
        coroutineContextProvider: CoroutineContextProvider
    ): CurrencyRepository = CurrencyRepositoryImpl(
        currencyDao,
        assetToDataMapper,
        currencyDataModelToDomainMapper,
        currencySearchContentResolver,
        coroutineContextProvider
    )
}
