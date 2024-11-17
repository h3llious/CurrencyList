package com.nhatbui.currency.domain.model

sealed class CurrencyDomainModel(
    open val id: String,
    open val name: String,
    open val symbol: String
) {
    data class Crypto(
        override val id: String,
        override val name: String,
        override val symbol: String,
    ): CurrencyDomainModel(id = id, name = name, symbol = symbol)

    data class Fiat(
        override val id: String,
        override val name: String,
        override val symbol: String,
        val code: String
    ): CurrencyDomainModel(id = id, name = name, symbol = symbol)
}
