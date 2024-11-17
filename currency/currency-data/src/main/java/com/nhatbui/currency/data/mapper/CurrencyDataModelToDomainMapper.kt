package com.nhatbui.currency.data.mapper

import com.nhatbui.currency.data.model.CurrencyDataModel
import com.nhatbui.currency.domain.model.CurrencyDomainModel

class CurrencyDataModelToDomainMapper {
    fun map(input: CurrencyDataModel): CurrencyDomainModel = if (input.code == null) {
        CurrencyDomainModel.Crypto(
            id = input.id,
            name = input.name,
            symbol = input.symbol
        )
    } else {
        CurrencyDomainModel.Fiat(
            id = input.id,
            name = input.name,
            symbol = input.symbol,
            code = input.code
        )
    }
}
