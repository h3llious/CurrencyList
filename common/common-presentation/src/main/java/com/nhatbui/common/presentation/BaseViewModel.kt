package com.nhatbui.common.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhatbui.common.domain.BaseUseCase
import com.nhatbui.common.domain.UseCaseExecutor
import com.nhatbui.common.domain.UseCaseExecutorProvider
import com.nhatbui.common.domain.model.DomainException
import com.nhatbui.common.presentation.model.ErrorResponseEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<VIEW_STATE : PresentationState>(
    useCaseExecutorProvider: UseCaseExecutorProvider,
    initialState: VIEW_STATE
) : ViewModel() {
    private val useCaseExecutor: UseCaseExecutor = useCaseExecutorProvider(viewModelScope)
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _events = Channel<PresentationEvent>(BUFFERED)
    val events = _events.receiveAsFlow()

    protected fun updateState(updatedState: (lastState: VIEW_STATE) -> VIEW_STATE) {
        _state.update { viewState ->
            updatedState(viewState)
        }
    }

    protected fun sendEvent(event: PresentationEvent) {
        viewModelScope.launch {
            _events.send(event)
        }
    }

    protected fun <REQUEST, RESULT> BaseUseCase<REQUEST, RESULT>.start(
        value: REQUEST,
        onResult: (RESULT) -> Unit = {},
        onError: (DomainException) -> Unit = ::handleException
    ) = useCaseExecutor.execute(
        useCase = this,
        value = value,
        onResult = onResult,
        onError = { domainException ->
            onError(domainException)
        }
    )

    protected fun <RESULT> BaseUseCase<Unit, RESULT>.start(
        onResult: (RESULT) -> Unit = {},
        onError: (DomainException) -> Unit = ::handleException
    ) = useCaseExecutor.execute(
        useCase = this,
        onResult = onResult,
        onError = { domainException ->
            onError(domainException)
        }
    )

    private fun handleException(exception: DomainException) {
        sendEvent(ErrorResponseEvent(exception))
    }
}
