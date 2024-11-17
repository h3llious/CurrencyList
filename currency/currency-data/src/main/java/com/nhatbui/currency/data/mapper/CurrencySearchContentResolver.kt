package com.nhatbui.currency.data.mapper

import com.nhatbui.currency.domain.model.CurrencyDomainModel

class CurrencySearchContentResolver {
    fun containsQuery(dataModel: CurrencyDomainModel, query: String) =
        dataModel.name.hasWordStartWith(query) || dataModel.symbol.hasWordStartWith(query)

    private fun String.hasWordStartWith(query: String) = this.split(" ").any { word ->
        word.startsWith(query, ignoreCase = true)
    }
}
