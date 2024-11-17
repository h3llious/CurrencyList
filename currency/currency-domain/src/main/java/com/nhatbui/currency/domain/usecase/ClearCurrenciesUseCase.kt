package com.nhatbui.currency.domain.usecase

import com.nhatbui.common.domain.BackgroundExecutingUseCase
import com.nhatbui.common.domain.CoroutineContextProvider
import com.nhatbui.currency.domain.repository.CurrencyRepository

abstract class ClearCurrenciesUseCase(coroutineContextProvider: CoroutineContextProvider) :
    BackgroundExecutingUseCase<Unit, Unit>(coroutineContextProvider)

class ClearCurrenciesUseCaseImpl(
    private val currencyRepository: CurrencyRepository,
    coroutineContextProvider: CoroutineContextProvider
) : ClearCurrenciesUseCase(coroutineContextProvider) {
    override suspend fun executeInBackground(request: Unit) {
        return currencyRepository.deleteCurrencies()
    }
}
