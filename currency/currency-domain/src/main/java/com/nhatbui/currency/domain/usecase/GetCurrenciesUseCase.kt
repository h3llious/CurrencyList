package com.nhatbui.currency.domain.usecase

import com.nhatbui.common.domain.ContinuousExecutingUseCase
import com.nhatbui.common.domain.CoroutineContextProvider
import com.nhatbui.currency.domain.model.CurrencyDomainModel
import com.nhatbui.currency.domain.model.CurrencyRequestDomainModel
import com.nhatbui.currency.domain.repository.CurrencyRepository

abstract class GetCurrenciesUseCase(coroutineContextProvider: CoroutineContextProvider) :
    ContinuousExecutingUseCase<CurrencyRequestDomainModel, List<CurrencyDomainModel>>(
        coroutineContextProvider
    )

class GetCurrenciesUseCaseImpl(
    private val currencyRepository: CurrencyRepository,
    coroutineContextProvider: CoroutineContextProvider
) : GetCurrenciesUseCase(coroutineContextProvider) {
    override suspend fun executeInBackground(
        request: CurrencyRequestDomainModel,
        callback: (List<CurrencyDomainModel>) -> Unit
    ) {
        currencyRepository.getCurrencies(request).collect { currencies ->
            callback(currencies)
        }
    }
}
