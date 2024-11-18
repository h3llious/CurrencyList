package com.nhatbui.currency.domain.usecase

import com.nhatbui.common.domain.BackgroundExecutingUseCase
import com.nhatbui.common.domain.CoroutineContextProvider
import com.nhatbui.currency.domain.model.CurrencyTypeDomainModel
import com.nhatbui.currency.domain.repository.SettingsRepository

abstract class UpdateFilterPreferenceUseCase(coroutineContextProvider: CoroutineContextProvider) :
    BackgroundExecutingUseCase<CurrencyTypeDomainModel, Unit>(coroutineContextProvider)

class UpdateFilterPreferenceUseCaseImpl(
    private val settingsRepository: SettingsRepository,
    coroutineContextProvider: CoroutineContextProvider
) : UpdateFilterPreferenceUseCase(coroutineContextProvider) {
    override suspend fun executeInBackground(request: CurrencyTypeDomainModel) {
        settingsRepository.updateFilterPreference(request)
    }
}
