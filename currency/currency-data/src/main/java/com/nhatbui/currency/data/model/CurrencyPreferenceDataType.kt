package com.nhatbui.currency.data.model

import com.nhatbui.currency.domain.model.CurrencyTypeDomainModel

enum class CurrencyPreferenceDataType {
    CRYPTO, FIAT, ALL
}

fun CurrencyTypeDomainModel.toDataType() = when (this) {
    CurrencyTypeDomainModel.All -> CurrencyPreferenceDataType.ALL
    CurrencyTypeDomainModel.Crypto -> CurrencyPreferenceDataType.CRYPTO
    CurrencyTypeDomainModel.Fiat -> CurrencyPreferenceDataType.FIAT
}

fun CurrencyPreferenceDataType.toDomain() = when (this) {
    CurrencyPreferenceDataType.ALL -> CurrencyTypeDomainModel.All
    CurrencyPreferenceDataType.CRYPTO -> CurrencyTypeDomainModel.Crypto
    CurrencyPreferenceDataType.FIAT -> CurrencyTypeDomainModel.Fiat
}
