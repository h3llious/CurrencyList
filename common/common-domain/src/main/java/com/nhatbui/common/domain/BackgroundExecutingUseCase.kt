package com.nhatbui.common.domain

import kotlinx.coroutines.withContext

abstract class BackgroundExecutingUseCase<REQUEST, RESULT>(
    private val coroutineContextProvider: CoroutineContextProvider
) : BaseUseCase<REQUEST, RESULT> {
    final override suspend fun execute(input: REQUEST, onResult: (RESULT) -> Unit) {
            val result = withContext(coroutineContextProvider.io) {
                executeInBackground(input)
            }
            onResult(result)
    }

    abstract suspend fun executeInBackground(request: REQUEST): RESULT
}
