package com.nhatbui.common.domain

import com.nhatbui.common.domain.model.DomainException
import com.nhatbui.common.domain.model.UnknownDomainException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UseCaseExecutor(private var coroutineScope: CoroutineScope) {
    fun <OUTPUT> execute(
        useCase: BaseUseCase<Unit, OUTPUT>,
        onResult: (OUTPUT) -> Unit = {},
        onError: (DomainException) -> Unit = {}
    ) = execute(useCase, Unit, onResult, onError)

    fun <INPUT, OUTPUT> execute(
        useCase: BaseUseCase<INPUT, OUTPUT>,
        value: INPUT,
        onResult: (OUTPUT) -> Unit = {},
        onError: (DomainException) -> Unit = {}
    ): Job {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            val domainException =
                ((throwable as? DomainException) ?: UnknownDomainException(throwable))
            onError(domainException)
        }
        return coroutineScope.launch(coroutineExceptionHandler) {
            useCase.execute(value, onResult)
        }
    }
}
