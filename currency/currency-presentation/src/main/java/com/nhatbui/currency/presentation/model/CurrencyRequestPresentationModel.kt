package com.nhatbui.currency.presentation.model

import com.nhatbui.currency.domain.model.CurrencyRequestDomainModel
import com.nhatbui.currency.presentation.model.CurrencyRequestPresentationModel.All
import com.nhatbui.currency.presentation.model.CurrencyRequestPresentationModel.Crypto
import com.nhatbui.currency.presentation.model.CurrencyRequestPresentationModel.Fiat

sealed interface CurrencyRequestPresentationModel {
    data object All: CurrencyRequestPresentationModel
    data object Crypto: CurrencyRequestPresentationModel
    data object Fiat: CurrencyRequestPresentationModel
}

fun CurrencyRequestPresentationModel.toDomain() = when (this) {
    All -> CurrencyRequestDomainModel.All
    Crypto -> CurrencyRequestDomainModel.Crypto
    Fiat -> CurrencyRequestDomainModel.Fiat
}
