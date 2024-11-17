package com.nhatbui.currencylist.di

import com.nhatbui.currency.ui.di.CurrencyDependencies
import com.nhatbui.currency.ui.di.NavHostDependencies
import com.nhatbui.currency.ui.mapper.CurrencyPresentationToUiMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object UiModule {
    @Provides
    fun providesCurrencyPresentationToUiMapper() = CurrencyPresentationToUiMapper()

    @Provides
    fun providesCurrencyDependencies(
        currencyPresentationToUiMapper: CurrencyPresentationToUiMapper
    ) = CurrencyDependencies(currencyPresentationToUiMapper)

    @Provides
    fun providesNavHostDependencies(
        currencyDependencies: CurrencyDependencies
    ) = NavHostDependencies(currencyDependencies)
}
