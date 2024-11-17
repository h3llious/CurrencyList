package com.nhatbui.currency.data.repository

import com.nhatbui.common.data.AssetToDataMapper
import com.nhatbui.common.domain.CoroutineContextProvider
import com.nhatbui.currency.data.local.CurrencyDao
import com.nhatbui.currency.data.mapper.CurrencyDataModelToDomainMapper
import com.nhatbui.currency.data.model.CurrencyDataModel
import com.nhatbui.currency.domain.model.CurrenciesDomainException.EmptyCurrenciesDomainException
import com.nhatbui.currency.domain.model.CurrencyDomainModel
import com.nhatbui.currency.domain.model.CurrencyRequestDomainModel
import com.nhatbui.currency.domain.model.CurrencyRequestDomainModel.All
import com.nhatbui.currency.domain.model.CurrencyRequestDomainModel.Crypto
import com.nhatbui.currency.domain.model.CurrencyRequestDomainModel.Fiat
import com.nhatbui.currency.domain.repository.CurrencyRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CurrencyRepositoryImpl(
    private val currencyDao: CurrencyDao,
    private val assetToDataMapper: AssetToDataMapper<List<CurrencyDataModel>>,
    private val currencyDataModelToDomainMapper: CurrencyDataModelToDomainMapper,
    private val coroutineContextProvider: CoroutineContextProvider
) : CurrencyRepository {
    override suspend fun insertCurrencies() {
        val (cryptoCurrencies, fiatCurrencies) = coroutineScope {
            awaitAll(
                async(coroutineContextProvider.default) { assetToDataMapper.getDataFromJson("crypto-currency.json") },
                async(coroutineContextProvider.default) { assetToDataMapper.getDataFromJson("fiat-currency.json") }
            )
        }
        currencyDao.insertAll(cryptoCurrencies + fiatCurrencies)
    }

    override suspend fun deleteCurrencies() {
        val deletedCount = currencyDao.deleteAll()
        if (deletedCount == 0) throw EmptyCurrenciesDomainException()
    }

    override suspend fun getCurrencies(request: CurrencyRequestDomainModel): Flow<List<CurrencyDomainModel>> =
        currencyDao.getAll().map { currencies ->
            currencies.map(currencyDataModelToDomainMapper::map).filter { item ->
                when (request) {
                    All -> true
                    Crypto -> item is CurrencyDomainModel.Crypto
                    Fiat -> item is CurrencyDomainModel.Fiat
                }
            }
        }
}
