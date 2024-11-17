package com.nhatbui.currency.ui.mapper

import com.nhatbui.currency.presentation.model.CurrencyPresentationModel
import com.nhatbui.currency.presentation.model.CurrencyPresentationModel.Crypto
import com.nhatbui.currency.presentation.model.CurrencyPresentationModel.Fiat
import com.nhatbui.currency.ui.model.CurrencyUiModel

class CurrencyPresentationToUiMapper {
    fun map(input: CurrencyPresentationModel): CurrencyUiModel {
        val initial = getInitial(input.name)
        return when (input) {
            is Crypto -> CurrencyUiModel.Crypto(
                id = input.id,
                name = input.name,
                symbol = input.symbol,
                iconInitial = initial
            )

            is Fiat -> CurrencyUiModel.Fiat(
                id = input.id,
                name = input.name,
                symbol = input.symbol,
                iconInitial = initial,
                code = input.code
            )
        }
    }

    private fun getInitial(name: String): String =
        if (name.isBlank()) "" else name.first().toString()
}
