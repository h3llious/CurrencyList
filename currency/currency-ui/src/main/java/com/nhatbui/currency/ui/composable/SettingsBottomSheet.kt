package com.nhatbui.currency.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nhatbui.common.ui.theme.CurrencyTheme
import com.nhatbui.currency.ui.R
import com.nhatbui.currency.ui.model.CurrencyTypeUiModel
import com.nhatbui.currency.ui.model.CurrencyTypeUiModel.Crypto
import com.nhatbui.currency.ui.model.CurrencyTypeUiModel.Fiat
import com.nhatbui.currency.ui.model.CurrencyTypeUiModel.All
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsBottomSheet(
    shouldShowBottomSheet: Boolean,
    onDismiss: () -> Unit,
    onInsertCurrencies: () -> Unit,
    onClearCurrencies: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (shouldShowBottomSheet) {
        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = onDismiss,
        ) {
            Column {
                SettingsMenuItem(
                    action = stringResource(R.string.action_insert_currencies_label),
                    onSelect = {
                        onInsertCurrencies()
                        onDismiss()
                    }
                )
                SettingsMenuItem(
                    action = stringResource(R.string.action_clear_currencies_label),
                    onSelect = {
                        onClearCurrencies()
                        onDismiss()
                    }
                )
            }
        }
    }
}

@Composable
private fun SettingsMenuItem(
    action: String,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .padding(CurrencyTheme.dimensions.spacingLarge),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = action,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
