package com.nhatbui.currency.presentation.model

import com.nhatbui.common.presentation.PresentationState
import com.nhatbui.currency.presentation.model.CurrencyTypePresentationModel.All

data class CurrencyPresentationState(
    val currencies: List<CurrencyPresentationModel> = emptyList(),
    val currencyType: CurrencyTypePresentationModel = All
) : PresentationState
