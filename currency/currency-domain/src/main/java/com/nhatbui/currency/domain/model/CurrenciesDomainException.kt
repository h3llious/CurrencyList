package com.nhatbui.currency.domain.model

import com.nhatbui.common.domain.model.DomainException

sealed class CurrenciesDomainException: DomainException() {
    class EmptyCurrenciesDomainException: CurrenciesDomainException()
}
