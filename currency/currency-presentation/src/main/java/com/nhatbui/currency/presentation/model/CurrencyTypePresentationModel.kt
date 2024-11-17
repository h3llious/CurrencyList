package com.nhatbui.currency.presentation.model

import com.nhatbui.currency.domain.model.CurrencyRequestDomainModel
import com.nhatbui.currency.presentation.model.CurrencyTypePresentationModel.All
import com.nhatbui.currency.presentation.model.CurrencyTypePresentationModel.Crypto
import com.nhatbui.currency.presentation.model.CurrencyTypePresentationModel.Fiat

sealed interface CurrencyTypePresentationModel {
    data object All : CurrencyTypePresentationModel
    data object Crypto : CurrencyTypePresentationModel
    data object Fiat : CurrencyTypePresentationModel
}

fun CurrencyTypePresentationModel.toDomain() = when (this) {
    All -> CurrencyRequestDomainModel.All
    Crypto -> CurrencyRequestDomainModel.Crypto
    Fiat -> CurrencyRequestDomainModel.Fiat
}
