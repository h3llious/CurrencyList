package com.nhatbui.currency.ui.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nhatbui.common.ui.theme.CurrencyTheme
import com.nhatbui.currency.ui.R
import com.nhatbui.currency.ui.model.CurrencyTypeUiModel

@Composable
fun Header(
    currencyType: CurrencyTypeUiModel,
    onFilterClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(currencyType.name),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1
            )
        }
        SearchIcon(onSearchClick = onSearchClick)
        Spacer(Modifier.width(5.dp))
        FilterIcon(onFilterClick = onFilterClick)
        SettingsIcon(onSettingsClick = onSettingsClick)
    }
}

@Composable
private fun SearchIcon(
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(36.dp)
            .clip(CircleShape)
            .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
            .clickable { onSearchClick() }
            .padding(10.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_search),
            tint = MaterialTheme.colorScheme.secondary,
            contentDescription = null
        )
    }
}

@Composable
private fun FilterIcon(
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Icon(
        modifier = modifier
            .clip(CircleShape)
            .clickable { onFilterClick() }
            .padding(10.dp)
            .size(CurrencyTheme.dimensions.spacingLarge),
        painter = painterResource(R.drawable.ic_filter),
        tint = MaterialTheme.colorScheme.secondary,
        contentDescription = null
    )
}

@Composable
private fun SettingsIcon(
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Icon(
        modifier = modifier
            .clip(CircleShape)
            .clickable { onSettingsClick() }
            .padding(10.dp)
            .size(CurrencyTheme.dimensions.spacingLarge),
        painter = painterResource(R.drawable.ic_settings),
        tint = MaterialTheme.colorScheme.secondary,
        contentDescription = null
    )
}
