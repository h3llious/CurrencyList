package com.nhatbui.currency.ui.mapper

import com.nhatbui.currency.ui.model.CurrencyTypeUiModel
import com.nhatbui.currency.ui.model.EmptyCurrencyUiModel

class EmptyCurrencyUiResolver {
    fun resolve(isSearching: Boolean, currencyType: CurrencyTypeUiModel): EmptyCurrencyUiModel {
        return when {
            !isSearching -> EmptyCurrencyUiModel.EmptyCurrency
            currencyType is CurrencyTypeUiModel.Fiat -> EmptyCurrencyUiModel.EmptySearchFiat
            else -> EmptyCurrencyUiModel.EmptySearchCrypto
        }
    }
}
