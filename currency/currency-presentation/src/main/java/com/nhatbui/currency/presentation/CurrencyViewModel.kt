package com.nhatbui.currency.presentation

import com.nhatbui.common.domain.UseCaseExecutorProvider
import com.nhatbui.common.presentation.BaseViewModel
import com.nhatbui.currency.domain.ExampleUseCase
import com.nhatbui.currency.presentation.model.CurrencyPresentationState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val exampleUseCase: ExampleUseCase,
    useCaseExecutorProvider: UseCaseExecutorProvider
): BaseViewModel<CurrencyPresentationState>(
    useCaseExecutorProvider,
    CurrencyPresentationState()
) {
    fun getExampleName() {
        exampleUseCase.start(
            onResult = { result ->
                updateState { lastState -> lastState.copy(name = result) }
            }
        )
    }
}
