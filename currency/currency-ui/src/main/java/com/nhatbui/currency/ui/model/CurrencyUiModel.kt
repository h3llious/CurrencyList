package com.nhatbui.currency.ui.model

sealed class CurrencyUiModel(
    open val id: String,
    open val name: String,
    open val symbol: String,
    open val iconInitial: String
) {
    data class Crypto(
        override val id: String,
        override val name: String,
        override val symbol: String,
        override val iconInitial: String
    ) : CurrencyUiModel(id = id, name = name, symbol = symbol, iconInitial = iconInitial)

    data class Fiat(
        override val id: String,
        override val name: String,
        override val symbol: String,
        override val iconInitial: String,
        val code: String
    ) : CurrencyUiModel(id = id, name = name, symbol = symbol, iconInitial = iconInitial)
}
