package com.nhatbui.currency.ui.model

import androidx.annotation.StringRes
import com.nhatbui.currency.ui.R

sealed class EmptyCurrencyUiModel(
    @StringRes open val title: Int,
    @StringRes open val subtitle: Int
) {
    data object EmptyCurrency: EmptyCurrencyUiModel(
        R.string.empty_currencies_text_description,
        R.string.empty_currencies_text_suggestion
    )
    data object EmptySearchCrypto: EmptyCurrencyUiModel(
        R.string.empty_search_text_description,
        R.string.empty_search_text_suggestion_crypto
    )
    data object EmptySearchFiat: EmptyCurrencyUiModel(
        R.string.empty_search_text_description,
        R.string.empty_search_text_suggestion_fiat
    )
}
