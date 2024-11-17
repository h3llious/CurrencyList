package com.nhatbui.currency.presentation.model

import com.nhatbui.currency.domain.model.CurrencyDomainModel
import com.nhatbui.currency.domain.model.CurrencyDomainModel.Crypto
import com.nhatbui.currency.domain.model.CurrencyDomainModel.Fiat

sealed class CurrencyPresentationModel(
    open val id: String,
    open val name: String,
    open val symbol: String
) {
    data class Crypto(
        override val id: String,
        override val name: String,
        override val symbol: String,
    ) : CurrencyPresentationModel(id = id, name = name, symbol = symbol)

    data class Fiat(
        override val id: String,
        override val name: String,
        override val symbol: String,
        val code: String
    ) : CurrencyPresentationModel(id = id, name = name, symbol = symbol)
}

fun CurrencyDomainModel.toPresentation() = when (this) {
    is Crypto -> CurrencyPresentationModel.Crypto(id = id, name = name, symbol = symbol)
    is Fiat -> CurrencyPresentationModel.Fiat(id = id, name = name, symbol = symbol, code = code)
}
