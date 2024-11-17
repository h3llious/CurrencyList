package com.nhatbui.currency.presentation.model

import com.nhatbui.common.presentation.PresentationEvent

sealed class CurrencyPresentationEvent: PresentationEvent {
    data object CurrenciesInserted: CurrencyPresentationEvent()
    data object InsertFailed: CurrencyPresentationEvent()
}
