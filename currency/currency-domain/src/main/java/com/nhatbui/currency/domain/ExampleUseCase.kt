package com.nhatbui.currency.domain

import com.nhatbui.common.domain.BackgroundExecutingUseCase
import com.nhatbui.common.domain.CoroutineContextProvider

abstract class ExampleUseCase(coroutineContextProvider: CoroutineContextProvider) :
    BackgroundExecutingUseCase<Unit, String>(coroutineContextProvider)

class ExampleUseCaseImpl(
    coroutineContextProvider: CoroutineContextProvider
) : ExampleUseCase(
    coroutineContextProvider
) {
    override suspend fun executeInBackground(request: Unit): String {
        return "Android"
    }
}

