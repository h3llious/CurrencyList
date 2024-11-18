package com.nhatbui.currency.domain.usecase

import com.nhatbui.common.domain.ContinuousExecutingUseCase
import com.nhatbui.common.domain.CoroutineContextProvider
import com.nhatbui.currency.domain.model.CurrencyTypeDomainModel
import com.nhatbui.currency.domain.repository.SettingsRepository

abstract class GetFilterPreferenceUseCase(coroutineContextProvider: CoroutineContextProvider) :
    ContinuousExecutingUseCase<Unit, CurrencyTypeDomainModel>(
        coroutineContextProvider
    )

class GetFilterPreferenceUseCaseImpl(
    private val settingsRepository: SettingsRepository,
    coroutineContextProvider: CoroutineContextProvider
) : GetFilterPreferenceUseCase(coroutineContextProvider) {
    override suspend fun executeInBackground(
        request: Unit,
        callback: (CurrencyTypeDomainModel) -> Unit
    ) {
        settingsRepository.getFilterPreference().collect { filter ->
            callback(filter)
        }
    }
}
