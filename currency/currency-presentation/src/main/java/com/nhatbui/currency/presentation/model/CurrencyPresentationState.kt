package com.nhatbui.currency.presentation.model

import com.nhatbui.common.presentation.PresentationState

data class CurrencyPresentationState(
    override val isError: Boolean = false,
    override val isLoading: Boolean = false,
    val name: String = ""
) : PresentationState
