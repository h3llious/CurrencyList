package com.nhatbui.currency.ui.model

import androidx.annotation.StringRes
import com.nhatbui.currency.presentation.model.CurrencyTypePresentationModel
import com.nhatbui.currency.ui.R
import com.nhatbui.currency.ui.model.CurrencyTypeUiModel.All
import com.nhatbui.currency.ui.model.CurrencyTypeUiModel.Crypto
import com.nhatbui.currency.ui.model.CurrencyTypeUiModel.Fiat

sealed class CurrencyTypeUiModel(
    @StringRes open val name: Int
) {
    data object Crypto: CurrencyTypeUiModel(name = R.string.currency_type_crypto_label)
    data object Fiat: CurrencyTypeUiModel(name = R.string.currency_type_fiat_label)
    data object All: CurrencyTypeUiModel(name = R.string.currency_type_all_label)
}

internal fun CurrencyTypeUiModel.toPresentation() = when (this) {
    Crypto -> CurrencyTypePresentationModel.Crypto
    Fiat -> CurrencyTypePresentationModel.Fiat
    All -> CurrencyTypePresentationModel.All
}

internal fun CurrencyTypePresentationModel.toUi() = when (this) {
    CurrencyTypePresentationModel.Crypto -> Crypto
    CurrencyTypePresentationModel.Fiat -> Fiat
    CurrencyTypePresentationModel.All -> All
}
