package com.nhatbui.currencylist.di

import com.nhatbui.currency.ui.di.CurrencyDependencies
import com.nhatbui.currency.ui.di.NavHostDependencies
import com.nhatbui.currency.ui.mapper.CurrencyPresentationToUiMapper
import com.nhatbui.currency.ui.mapper.EmptyCurrencyUiResolver
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
    fun providesEmptyCurrencyUiResolver() = EmptyCurrencyUiResolver()

    @Provides
    fun providesCurrencyDependencies(
        currencyPresentationToUiMapper: CurrencyPresentationToUiMapper,
        emptyCurrencyUiResolver: EmptyCurrencyUiResolver
    ) = CurrencyDependencies(currencyPresentationToUiMapper, emptyCurrencyUiResolver)

    @Provides
    fun providesNavHostDependencies(
        currencyDependencies: CurrencyDependencies
    ) = NavHostDependencies(currencyDependencies)
}
