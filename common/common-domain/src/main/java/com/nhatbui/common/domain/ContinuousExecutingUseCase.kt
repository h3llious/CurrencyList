package com.nhatbui.common.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class ContinuousExecutingUseCase<REQUEST, RESULT>(
    private val coroutineContextProvider: CoroutineContextProvider
) : BaseUseCase<REQUEST, RESULT> {
    final override suspend fun execute(input: REQUEST, onResult: (RESULT) -> Unit) {
        withContext(coroutineContextProvider.io) {
            executeInBackground(input) { result ->
                CoroutineScope(coroutineContextProvider.main).launch {
                    onResult(result)
                }
            }
        }
    }

    abstract suspend fun executeInBackground(
        request: REQUEST,
        callback: (RESULT) -> Unit
    )
}
