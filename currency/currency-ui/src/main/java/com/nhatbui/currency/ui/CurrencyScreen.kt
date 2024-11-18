package com.nhatbui.currency.ui

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nhatbui.common.presentation.PresentationEvent
import com.nhatbui.common.presentation.model.ErrorResponseEvent
import com.nhatbui.common.ui.component.Screen
import com.nhatbui.common.ui.theme.CurrencyTheme
import com.nhatbui.currency.presentation.CurrencyViewModel
import com.nhatbui.currency.presentation.model.CurrencyPresentationEvent.CurrenciesInserted
import com.nhatbui.currency.presentation.model.CurrencyPresentationEvent.CurrenciesCleared
import com.nhatbui.currency.presentation.model.CurrencyPresentationEvent.ClearFailedEmptyCurrencies
import com.nhatbui.currency.presentation.model.CurrencyPresentationEvent.ClearFailed
import com.nhatbui.currency.presentation.model.CurrencyPresentationEvent.InsertFailed
import com.nhatbui.currency.presentation.model.CurrencyPresentationState
import com.nhatbui.currency.ui.composable.CurrencyItem
import com.nhatbui.currency.ui.composable.FilterBottomSheet
import com.nhatbui.currency.ui.composable.Header
import com.nhatbui.currency.ui.composable.LocalSnackbarHostState
import com.nhatbui.currency.ui.composable.SettingsBottomSheet
import com.nhatbui.currency.ui.di.CurrencyDependencies
import com.nhatbui.currency.ui.model.CurrencyTypeUiModel
import com.nhatbui.currency.ui.model.CurrencyUiModel
import com.nhatbui.currency.ui.model.EmptyCurrencyUiModel
import com.nhatbui.currency.ui.model.toPresentation
import com.nhatbui.currency.ui.model.toUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

private const val QUERY_DEBOUNCE_TIME = 200L

@OptIn(FlowPreview::class)
@Composable
internal fun CurrencyDependencies.CurrencyScreen() =
    Screen<CurrencyPresentationState, CurrencyViewModel> {
        val snackbarHostState = LocalSnackbarHostState.current
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        var showFilterBottomSheet by remember { mutableStateOf(false) }
        var showSettingsBottomSheet by remember { mutableStateOf(false) }
        var showSearchBar by remember { mutableStateOf(false) }
        var searchText by remember { mutableStateOf("") }
        ObserveViewModelEvents { event ->
            handleEvents(event, scope, context, snackbarHostState)
        }
        Content { viewState ->
            LaunchedEffect(Unit) {
                viewModel.getCurrencies(viewState.currencyType, searchText)
            }
            LaunchedEffect(searchText) {
                snapshotFlow { searchText }
                    .debounce(QUERY_DEBOUNCE_TIME)
                    .collectLatest { query ->
                        viewModel.getCurrencies(viewState.currencyType, query)
                    }
            }
            val currencies = remember(viewState.currencies) {
                viewState.currencies.map(currencyPresentationToUiMapper::map)
            }
            val currencyType = remember(viewState.currencyType) { viewState.currencyType.toUi() }
            val emptyType = remember(currencyType, showSearchBar) {
                emptyCurrencyUiResolver.resolve(
                    isSearching = showSearchBar,
                    currencyType = currencyType
                )
            }
            CurrencyScreenContent(
                currencyType = currencyType,
                emptyType = emptyType,
                currencies = currencies.toImmutableList(),
                isSearching = showSearchBar,
                onSettingsClick = { showSettingsBottomSheet = true },
                onFilterClick = { showFilterBottomSheet = true },
                onSearchClick = { showSearchBar = true },
                onSearchCancel = {
                    showSearchBar = false
                    searchText = ""
                },
                onSearching = { searchingValue ->
                    searchText = searchingValue
                },
                onInsertCurrencies = {
                    viewModel.onInsertCurrencies()
                }
            )
            FilterBottomSheet(
                shouldShowBottomSheet = showFilterBottomSheet,
                onDismiss = { showFilterBottomSheet = false },
                onSelectType = { type ->
                    viewModel.getCurrencies(type.toPresentation(), searchText)
                },
                currentSelectedType = currencyType
            )
            SettingsBottomSheet(
                shouldShowBottomSheet = showSettingsBottomSheet,
                onDismiss = { showSettingsBottomSheet = false },
                onInsertCurrencies = {
                    viewModel.onInsertCurrencies()
                },
                onClearCurrencies = {
                    viewModel.onClearCurrencies()
                }
            )
        }
    }

@Composable
private fun CurrencyScreenContent(
    currencies: ImmutableList<CurrencyUiModel>,
    currencyType: CurrencyTypeUiModel,
    emptyType: EmptyCurrencyUiModel,
    isSearching: Boolean,
    onSearchClick: () -> Unit,
    onFilterClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onSearchCancel: () -> Unit,
    onSearching: (String) -> Unit,
    onInsertCurrencies: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Header(
            modifier = Modifier
                .padding(horizontal = CurrencyTheme.dimensions.spacingMedium)
                .height(60.dp),
            currencyType = currencyType,
            isSearching = isSearching,
            onFilterClick = onFilterClick,
            onSearchClick = onSearchClick,
            onSettingsClick = onSettingsClick,
            onSearchCancel = onSearchCancel,
            onSearching = onSearching
        )
        when {
            currencies.isEmpty() -> EmptyView(emptyType, onInsertCurrencies)
            else -> {
                LazyColumn(
                    state = rememberLazyListState()
                ) {
                    itemsIndexed(currencies) { _, currency ->
                        CurrencyItem(currency)
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyView(
    emptyType: EmptyCurrencyUiModel,
    onInsertCurrencies: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(CurrencyTheme.dimensions.spacingLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(40.dp),
            painter = painterResource(R.drawable.ic_empty_view),
            tint = MaterialTheme.colorScheme.secondary,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(CurrencyTheme.dimensions.spacingLarge))
        Text(
            text = stringResource(emptyType.title),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(emptyType.subtitle),
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.bodyLarge
        )
        if (emptyType is EmptyCurrencyUiModel.EmptyCurrency) {
            Spacer(modifier = Modifier.height(CurrencyTheme.dimensions.spacingLarge))
            Button(
                onClick = { onInsertCurrencies() }
            ) {
                Text(text = stringResource(R.string.action_insert_currencies_label))
            }
        }
    }
}

private fun handleEvents(
    event: PresentationEvent,
    scope: CoroutineScope,
    context: Context,
    snackbarHostState: SnackbarHostState
) {
    scope.launch {
        when (event) {
            CurrenciesInserted -> {
                snackbarHostState.showSnackbarMessage(context.getString(R.string.insert_currencies_success_message))
            }

            CurrenciesCleared -> {
                snackbarHostState.showSnackbarMessage(context.getString(R.string.clear_currencies_success_message))
            }

            ClearFailedEmptyCurrencies -> {
                snackbarHostState.showSnackbarMessage(context.getString(R.string.clear_currencies_failed_no_currencies_message))
            }

            is ErrorResponseEvent -> {
                snackbarHostState.showSnackbarMessage(
                    context.getString(
                        R.string.error_message_with_cause,
                        event.cause?.message ?: ""
                    )
                )
            }

            InsertFailed, ClearFailed -> {
                snackbarHostState.showSnackbarMessage(context.getString(R.string.generic_error_message))
            }

            else -> {
                snackbarHostState.showSnackbarMessage(context.getString(R.string.unknown_message))
            }
        }
    }
}

private suspend fun SnackbarHostState.showSnackbarMessage(message: String) {
    showSnackbar(message, withDismissAction = true)
}
