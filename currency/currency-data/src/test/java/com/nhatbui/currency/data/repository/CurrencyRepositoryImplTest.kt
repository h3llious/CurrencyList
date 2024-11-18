package com.nhatbui.currency.data.repository

import com.nhatbui.common.data.AssetToDataMapper
import com.nhatbui.common.domain.CoroutineContextProvider
import com.nhatbui.currency.data.local.CurrencyDao
import com.nhatbui.currency.data.mapper.CurrencyDataModelToDomainMapper
import com.nhatbui.currency.data.mapper.CurrencySearchContentResolver
import com.nhatbui.currency.data.model.CurrencyDataModel
import com.nhatbui.currency.domain.model.CurrenciesDomainException.EmptyCurrenciesDomainException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.given
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class CurrencyRepositoryImplTest {

    private lateinit var classUnderTest: CurrencyRepositoryImpl

    @Mock
    private lateinit var currencyDao: CurrencyDao

    @Mock
    private lateinit var assetToDataMapper: AssetToDataMapper<List<CurrencyDataModel>>

    @Mock
    private lateinit var currencyDataModelToDomainMapper: CurrencyDataModelToDomainMapper

    @Mock
    private lateinit var currencySearchContentResolver: CurrencySearchContentResolver

    @Mock
    private lateinit var coroutineContextProvider: CoroutineContextProvider

    @Before
    fun setup() {
        classUnderTest = CurrencyRepositoryImpl(
            currencyDao,
            assetToDataMapper,
            currencyDataModelToDomainMapper,
            currencySearchContentResolver,
            coroutineContextProvider
        )
    }

    @Test
    fun `When deleteCurrencies Then verify database action is called`() = runTest {
        // Given
        given(currencyDao.deleteAll()).willReturn(2)

        // When
        classUnderTest.deleteCurrencies()

        // Then
        verify(currencyDao, times(1)).deleteAll()
    }

    @Test(expected = EmptyCurrenciesDomainException::class)
    fun `Given no data to delete When deleteCurrencies Then throw expected exception`() = runTest {
        // Given
        given(currencyDao.deleteAll()).willReturn(0)

        // When
        classUnderTest.deleteCurrencies()

        // Then
        verify(currencyDao, times(1)).deleteAll()
    }

    @Test
    fun `When insertCurrencies Then verify database action is called`() = runTest {
        // Given
        val givenCryptoDataModel = CurrencyDataModel("id", "name", "symbol", null)
        val givenFiatDataModel = CurrencyDataModel("id2", "fiat", "$", "USD")
        given(coroutineContextProvider.default).willReturn(Dispatchers.Unconfined)
        given(
            assetToDataMapper.getDataFromJson("crypto-currency.json")
        ).willReturn(listOf(givenCryptoDataModel))
        given(
            assetToDataMapper.getDataFromJson("fiat-currency.json")
        ).willReturn(
            listOf(givenFiatDataModel)
        )

        // When
        classUnderTest.insertCurrencies()

        // Then
        verify(currencyDao, times(1)).insertAll(
            listOf(givenCryptoDataModel, givenFiatDataModel)
        )
    }
}
