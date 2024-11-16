package com.nhatbui.common.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.nhatbui.common.presentation.BaseViewModel
import com.nhatbui.common.presentation.PresentationEvent
import com.nhatbui.common.presentation.PresentationState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Stable
class ViewScope<State : PresentationState, ViewModel : BaseViewModel<State>>(val viewModel: ViewModel) {

    @Composable
    fun ObserveViewModelEvents(onEvent: (event: PresentationEvent) -> Unit) {
        val lifecycleOwner = LocalLifecycleOwner.current
        LaunchedEffect(viewModel.events, lifecycleOwner.lifecycle) {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                withContext(Dispatchers.Main.immediate) {
                    viewModel.events.collect { event ->
                        onEvent(event)
                    }
                }
            }
        }
    }

    @Composable
    fun Content(
        content: @Composable (State) -> Unit
    ) {
        val state by viewModel.state.collectAsStateWithLifecycle()
        content(state)
    }
}
