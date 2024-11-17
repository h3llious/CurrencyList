package com.nhatbui.currency.presentation.model

import com.nhatbui.common.presentation.PresentationEvent

sealed class CurrencyPresentationEvent: PresentationEvent {
    data object CurrenciesInserted: CurrencyPresentationEvent()
    data object InsertFailed: CurrencyPresentationEvent()
    data object CurrenciesCleared: CurrencyPresentationEvent()
    data object ClearFailed: CurrencyPresentationEvent()
    data object ClearFailedEmptyCurrencies: CurrencyPresentationEvent()
}
