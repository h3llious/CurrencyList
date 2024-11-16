package com.nhatbui.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.nhatbui.common.presentation.BaseViewModel
import com.nhatbui.common.presentation.PresentationState

@Composable
inline fun <State : PresentationState, reified ViewModel : BaseViewModel<State>> Screen(
    content: @Composable ViewScope<State, ViewModel>.() -> Unit
) {
    val viewModel: ViewModel = hiltViewModel()
    val scope = remember(viewModel) { ViewScope(viewModel) }

    with(scope) {
        content()
    }
}
