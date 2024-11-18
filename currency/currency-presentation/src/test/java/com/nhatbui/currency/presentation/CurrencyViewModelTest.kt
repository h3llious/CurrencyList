package com.nhatbui.currency.presentation

import com.nhatbui.common.domain.UseCaseExecutor
import com.nhatbui.common.domain.model.UnknownDomainException
import com.nhatbui.currency.domain.model.CurrencyDomainModel
import com.nhatbui.currency.domain.model.CurrencyRequestDomainModel
import com.nhatbui.currency.domain.model.CurrencyTypeDomainModel
import com.nhatbui.currency.domain.usecase.ClearCurrenciesUseCase
import com.nhatbui.currency.domain.usecase.GetCurrenciesUseCase
import com.nhatbui.currency.domain.usecase.InsertCurrenciesUseCase
import com.nhatbui.currency.presentation.model.CurrencyPresentationEvent.CurrenciesInserted
import com.nhatbui.currency.presentation.model.CurrencyPresentationEvent.InsertFailed
import com.nhatbui.currency.presentation.model.CurrencyPresentationModel
import com.nhatbui.currency.presentation.model.CurrencyPresentationState
import com.nhatbui.currency.presentation.model.CurrencyTypePresentationModel
import com.nhatbui.unit.test.MainDispatcherRule
import com.nhatbui.unit.test.givenFailedNoArgumentUseCaseExecution
import com.nhatbui.unit.test.givenSuccessfulNoArgumentUseCaseExecution
import com.nhatbui.unit.test.givenSuccessfulUseCaseExecution
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CurrencyViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var classUnderTest: CurrencyViewModel

    @Mock
    private lateinit var getCurrenciesUseCase: GetCurrenciesUseCase

    @Mock
    private lateinit var insertCurrenciesUseCase: InsertCurrenciesUseCase

    @Mock
    private lateinit var clearCurrenciesUseCase: ClearCurrenciesUseCase

    @Mock
    private lateinit var useCaseExecutor: UseCaseExecutor

    @Before
    fun setup() {
        classUnderTest = CurrencyViewModel(
            getCurrenciesUseCase,
            insertCurrenciesUseCase,
            clearCurrenciesUseCase,
            { useCaseExecutor }
        )
    }

    @Test
    fun `When onInsertCurrencies successfully Then emit expected event`() = runTest {
        // Given
        val expectedEvent = CurrenciesInserted

        useCaseExecutor.givenSuccessfulNoArgumentUseCaseExecution(
            useCase = insertCurrenciesUseCase,
            result = Unit
        )

        // When
        classUnderTest.onInsertCurrencies()
        val actualEvent = classUnderTest.events.first()

        // Then
        assertEquals(expectedEvent, actualEvent)
    }

    @Test
    fun `When onInsertCurrencies failed Then emit expected error event`() = runTest {
        // Given
        val expectedEvent = InsertFailed

        useCaseExecutor.givenFailedNoArgumentUseCaseExecution(
            useCase = insertCurrenciesUseCase,
            domainException = UnknownDomainException()
        )

        // When
        classUnderTest.onInsertCurrencies()
        val actualEvent = classUnderTest.events.first()

        // Then
        assertEquals(expectedEvent, actualEvent)
    }

    @Test
    fun `When getCurrencies successful Then emit expected result`() = runTest {
        // Given
        val givenResult = listOf(
            CurrencyDomainModel.Crypto("id", "name", "symbol")
        )
        val givenRequest = CurrencyRequestDomainModel(
            type = CurrencyTypeDomainModel.All,
            query = ""
        )
        val expectedState = CurrencyPresentationState(
            currencies = listOf(
                CurrencyPresentationModel.Crypto("id", "name", "symbol")
            ),
            currencyType = CurrencyTypePresentationModel.All
        )

        useCaseExecutor.givenSuccessfulUseCaseExecution(
            useCase = getCurrenciesUseCase,
            input = givenRequest,
            result = givenResult
        )

        // When
        classUnderTest.getCurrencies(
            type = CurrencyTypePresentationModel.All,
            searchContent = ""
        )
        val actualEvent = classUnderTest.state.first()

        // Then
        assertEquals(expectedState, actualEvent)
    }
}
