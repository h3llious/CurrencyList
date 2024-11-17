package com.nhatbui.currency.presentation

import com.nhatbui.common.domain.UseCaseExecutorProvider
import com.nhatbui.common.presentation.BaseViewModel
import com.nhatbui.currency.domain.model.CurrencyDomainModel
import com.nhatbui.currency.domain.usecase.GetCurrenciesUseCase
import com.nhatbui.currency.domain.usecase.InsertCurrenciesUseCase
import com.nhatbui.currency.presentation.model.CurrencyPresentationEvent.CurrenciesInserted
import com.nhatbui.currency.presentation.model.CurrencyPresentationEvent.InsertFailed
import com.nhatbui.currency.presentation.model.CurrencyPresentationState
import com.nhatbui.currency.presentation.model.CurrencyRequestPresentationModel
import com.nhatbui.currency.presentation.model.toDomain
import com.nhatbui.currency.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val insertCurrenciesUseCase: InsertCurrenciesUseCase,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<CurrencyPresentationState>(
    useCaseExecutorProvider,
    CurrencyPresentationState()
) {
    private var getCurrenciesJob: Job? = null

    fun insertCurrencies() {
        insertCurrenciesUseCase.start(
            onResult = {
                sendEvent(CurrenciesInserted)
            },
            onError = {
                sendEvent(InsertFailed)
            }
        )
    }

    fun getCurrencies(request: CurrencyRequestPresentationModel) {
        getCurrenciesJob?.cancel()
        getCurrenciesJob = getCurrenciesUseCase.start(
            value = request.toDomain(),
            onResult = { currencies ->
                updateState { lastState ->
                    lastState.copy(
                        currencies = currencies.map(
                            CurrencyDomainModel::toPresentation
                        )
                    )
                }
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        getCurrenciesJob?.cancel()
    }
}
