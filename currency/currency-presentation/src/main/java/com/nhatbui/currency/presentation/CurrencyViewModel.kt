package com.nhatbui.currency.presentation

import com.nhatbui.common.domain.UseCaseExecutorProvider
import com.nhatbui.common.presentation.BaseViewModel
import com.nhatbui.currency.domain.model.CurrenciesDomainException.EmptyCurrenciesDomainException
import com.nhatbui.currency.domain.model.CurrencyDomainModel
import com.nhatbui.currency.domain.model.CurrencyRequestDomainModel
import com.nhatbui.currency.domain.usecase.ClearCurrenciesUseCase
import com.nhatbui.currency.domain.usecase.GetCurrenciesUseCase
import com.nhatbui.currency.domain.usecase.GetFilterPreferenceUseCase
import com.nhatbui.currency.domain.usecase.InsertCurrenciesUseCase
import com.nhatbui.currency.domain.usecase.UpdateFilterPreferenceUseCase
import com.nhatbui.currency.presentation.model.CurrencyPresentationEvent.ClearFailed
import com.nhatbui.currency.presentation.model.CurrencyPresentationEvent.ClearFailedEmptyCurrencies
import com.nhatbui.currency.presentation.model.CurrencyPresentationEvent.CurrenciesCleared
import com.nhatbui.currency.presentation.model.CurrencyPresentationEvent.CurrenciesInserted
import com.nhatbui.currency.presentation.model.CurrencyPresentationEvent.FilterPreferenceUpdated
import com.nhatbui.currency.presentation.model.CurrencyPresentationEvent.InsertFailed
import com.nhatbui.currency.presentation.model.CurrencyPresentationState
import com.nhatbui.currency.presentation.model.CurrencyTypePresentationModel
import com.nhatbui.currency.presentation.model.toDomain
import com.nhatbui.currency.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val insertCurrenciesUseCase: InsertCurrenciesUseCase,
    private val clearCurrenciesUseCase: ClearCurrenciesUseCase,
    private val updateFilterPreferenceUseCase: UpdateFilterPreferenceUseCase,
    private val getFilterPreferenceUseCase: GetFilterPreferenceUseCase,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<CurrencyPresentationState>(
    useCaseExecutorProvider,
    CurrencyPresentationState()
) {
    private var getCurrenciesJob: Job? = null
    private var getFilterJob: Job? = null

    fun onInsertCurrencies() {
        insertCurrenciesUseCase.start(
            onResult = {
                sendEvent(CurrenciesInserted)
            },
            onError = {
                sendEvent(InsertFailed)
            }
        )
    }

    fun onClearCurrencies() {
        clearCurrenciesUseCase.start(
            onResult = {
                sendEvent(CurrenciesCleared)
            },
            onError = { exception ->
                when (exception) {
                    is EmptyCurrenciesDomainException -> sendEvent(ClearFailedEmptyCurrencies)
                    else -> sendEvent(ClearFailed)
                }
            }
        )
    }

    fun onUpdateFilter(type: CurrencyTypePresentationModel) {
        updateFilterPreferenceUseCase.start(
            value = type.toDomain()
        )
    }

    fun fetchFilterPreference() {
        getFilterJob?.cancel()
        getFilterJob = getFilterPreferenceUseCase.start(
            onResult = { type ->
                val presentationType = type.toPresentation()
                updateState { lastState -> lastState.copy(currencyType = presentationType) }
                sendEvent(FilterPreferenceUpdated(presentationType))
            }
        )
    }

    fun getCurrencies(type: CurrencyTypePresentationModel, searchContent: String) {
        getCurrenciesJob?.cancel()
        getCurrenciesJob = getCurrenciesUseCase.start(
            value = CurrencyRequestDomainModel(
                type = type.toDomain(),
                query = searchContent
            ),
            onResult = { currencies ->
                updateState { lastState ->
                    lastState.copy(
                        currencies = currencies.map(CurrencyDomainModel::toPresentation),
                        currencyType = type
                    )
                }
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        getCurrenciesJob?.cancel()
        getFilterJob?.cancel()
    }
}
