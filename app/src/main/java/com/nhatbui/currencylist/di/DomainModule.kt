package com.nhatbui.currencylist.di

import com.nhatbui.common.domain.CoroutineContextProvider
import com.nhatbui.currency.domain.repository.CurrencyRepository
import com.nhatbui.currency.domain.repository.SettingsRepository
import com.nhatbui.currency.domain.usecase.ClearCurrenciesUseCase
import com.nhatbui.currency.domain.usecase.ClearCurrenciesUseCaseImpl
import com.nhatbui.currency.domain.usecase.GetCurrenciesUseCase
import com.nhatbui.currency.domain.usecase.GetCurrenciesUseCaseImpl
import com.nhatbui.currency.domain.usecase.GetFilterPreferenceUseCase
import com.nhatbui.currency.domain.usecase.GetFilterPreferenceUseCaseImpl
import com.nhatbui.currency.domain.usecase.InsertCurrenciesUseCase
import com.nhatbui.currency.domain.usecase.InsertCurrenciesUseCaseImpl
import com.nhatbui.currency.domain.usecase.UpdateFilterPreferenceUseCase
import com.nhatbui.currency.domain.usecase.UpdateFilterPreferenceUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    fun providesGetCurrenciesUseCase(
        repository: CurrencyRepository,
        coroutineContextProvider: CoroutineContextProvider
    ): GetCurrenciesUseCase = GetCurrenciesUseCaseImpl(repository, coroutineContextProvider)

    @Provides
    fun providesInsertCurrenciesUseCase(
        repository: CurrencyRepository,
        coroutineContextProvider: CoroutineContextProvider
    ): InsertCurrenciesUseCase = InsertCurrenciesUseCaseImpl(repository, coroutineContextProvider)

    @Provides
    fun providesClearCurrenciesUseCase(
        repository: CurrencyRepository,
        coroutineContextProvider: CoroutineContextProvider
    ): ClearCurrenciesUseCase = ClearCurrenciesUseCaseImpl(repository, coroutineContextProvider)

    @Provides
    fun providesGetFilterPreferenceUseCase(
        repository: SettingsRepository,
        coroutineContextProvider: CoroutineContextProvider
    ): GetFilterPreferenceUseCase =
        GetFilterPreferenceUseCaseImpl(repository, coroutineContextProvider)

    @Provides
    fun providesUpdateFilterPreferenceUseCase(
        repository: SettingsRepository,
        coroutineContextProvider: CoroutineContextProvider
    ): UpdateFilterPreferenceUseCase =
        UpdateFilterPreferenceUseCaseImpl(repository, coroutineContextProvider)
}
