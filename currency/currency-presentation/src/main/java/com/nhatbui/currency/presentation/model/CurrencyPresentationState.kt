package com.nhatbui.currency.presentation.model

import com.nhatbui.common.presentation.PresentationState

data class CurrencyPresentationState(
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val currencies: List<CurrencyPresentationModel> = emptyList()
) : PresentationState
