package com.nhatbui.currency.ui.di

import com.nhatbui.currency.ui.mapper.CurrencyPresentationToUiMapper
import com.nhatbui.currency.ui.mapper.EmptyCurrencyUiResolver

data class CurrencyDependencies(
    val currencyPresentationToUiMapper: CurrencyPresentationToUiMapper,
    val emptyCurrencyUiResolver: EmptyCurrencyUiResolver
)
