package com.nhatbui.currency.domain.model

sealed interface CurrencyTypeDomainModel {
    data object Crypto: CurrencyTypeDomainModel
    data object Fiat: CurrencyTypeDomainModel
    data object All: CurrencyTypeDomainModel
}
