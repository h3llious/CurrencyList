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
internal fun FilterBottomSheet(
    shouldShowBottomSheet: Boolean,
    currentSelectedType: CurrencyTypeUiModel,
    onDismiss: () -> Unit,
    onSelectType: (CurrencyTypeUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val filterTypes = persistentListOf(Crypto, Fiat, All)
    if (shouldShowBottomSheet) {
        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = onDismiss,
        ) {
            Column {
                filterTypes.forEach { type ->
                    FilterMenuItem(
                        type = type,
                        isSelected = currentSelectedType == type,
                        onSelectType = { selectedType ->
                            onSelectType(selectedType)
                            onDismiss()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun FilterMenuItem(
    type: CurrencyTypeUiModel,
    isSelected: Boolean,
    onSelectType: (CurrencyTypeUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSelectType(type) }
            .padding(CurrencyTheme.dimensions.spacingLarge),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(type.name),
                color = MaterialTheme.colorScheme.primary
            )
        }
        if (isSelected) {
            Icon(
                modifier = Modifier.size(36.dp),
                painter = painterResource(CurrencyTheme.icons.iconCheck),
                tint = MaterialTheme.colorScheme.surfaceTint,
                contentDescription = stringResource(R.string.selected_content_description)
            )
        }
    }
}
