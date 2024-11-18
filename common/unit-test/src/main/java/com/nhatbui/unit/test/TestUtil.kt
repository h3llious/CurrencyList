package com.nhatbui.unit.test

import com.nhatbui.common.domain.BaseUseCase
import com.nhatbui.common.domain.UseCaseExecutor
import com.nhatbui.common.domain.model.DomainException
import kotlinx.coroutines.Job
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.given
import org.mockito.kotlin.mock

fun <RESULT> UseCaseExecutor.givenSuccessfulNoArgumentUseCaseExecution(
    useCase: BaseUseCase<Unit, RESULT>,
    result: RESULT
) {
    given(
        this.execute(
            useCase = eq(useCase),
            onResult = any(),
            onError = any()
        )
    ).willAnswer { invocationOnMock ->
        (invocationOnMock.getArgument(1) as (RESULT) -> Unit)(result)
        mock<Job>()
    }
}

fun UseCaseExecutor.givenFailedNoArgumentUseCaseExecution(
    useCase: BaseUseCase<Unit, *>,
    domainException: DomainException
) {
    given(
        this.execute(
            useCase = eq(useCase),
            onResult = any(),
            onError = any()
        )
    ).willAnswer { invocationOnMock ->
        (invocationOnMock.getArgument(2) as (DomainException) -> Unit)(domainException)
        mock<Job>()
    }
}

fun <REQUEST, RESULT> UseCaseExecutor.givenSuccessfulUseCaseExecution(
    useCase: BaseUseCase<REQUEST, RESULT>,
    input: REQUEST,
    result: RESULT
) {
    given(
        this.execute(
            useCase = eq(useCase),
            value = eq(input),
            onResult = any(),
            onError = any()
        )
    ).willAnswer { invocationOnMock ->
        (invocationOnMock.getArgument(2) as (RESULT) -> Unit)(result)
        mock<Job>()
    }
}
