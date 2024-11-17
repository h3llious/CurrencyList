package com.nhatbui.currency.domain.model

data class CurrencyRequestDomainModel(
    val type: CurrencyTypeDomainModel,
    val query: String
)
