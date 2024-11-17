package com.nhatbui.currency.domain.usecase

import com.nhatbui.common.domain.BackgroundExecutingUseCase
import com.nhatbui.common.domain.CoroutineContextProvider
import com.nhatbui.currency.domain.repository.CurrencyRepository

abstract class InsertCurrenciesUseCase(coroutineContextProvider: CoroutineContextProvider) :
    BackgroundExecutingUseCase<Unit, Unit>(coroutineContextProvider)

class InsertCurrenciesUseCaseImpl(
    private val currencyRepository: CurrencyRepository,
    coroutineContextProvider: CoroutineContextProvider
) : InsertCurrenciesUseCase(coroutineContextProvider) {
    override suspend fun executeInBackground(request: Unit) {
        return currencyRepository.insertCurrencies()
    }
}
