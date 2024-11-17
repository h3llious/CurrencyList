package com.nhatbui.currency.domain.model

sealed interface CurrencyRequestDomainModel {
    data object Crypto: CurrencyRequestDomainModel
    data object Fiat: CurrencyRequestDomainModel
    data object All: CurrencyRequestDomainModel
}
