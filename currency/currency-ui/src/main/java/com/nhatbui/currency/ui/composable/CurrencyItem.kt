package com.nhatbui.currency.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nhatbui.common.ui.theme.CurrencyTheme
import com.nhatbui.currency.ui.R
import com.nhatbui.currency.ui.model.CurrencyUiModel
import com.nhatbui.currency.ui.model.CurrencyUiModel.Crypto

@Composable
internal fun CurrencyItem(item: CurrencyUiModel, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = CurrencyTheme.dimensions.spacingMedium,
                vertical = CurrencyTheme.dimensions.spacingLarge
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(CurrencyTheme.dimensions.spacingLarge)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = item.iconInitial,
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.labelLarge
            )
        }
        Spacer(modifier = Modifier.width(CurrencyTheme.dimensions.spacingMedium))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                modifier = Modifier.basicMarquee(),
                text = item.name,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1
            )
        }
        if (item is Crypto) {
            Spacer(modifier = Modifier.width(CurrencyTheme.dimensions.spacingMedium))
            Text(
                text = item.symbol,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.width(CurrencyTheme.dimensions.spacingMedium))
            Icon(
                modifier = Modifier.size(CurrencyTheme.dimensions.spacingMedium),
                painter = painterResource(CurrencyTheme.icons.iconRightArrow),
                tint = MaterialTheme.colorScheme.tertiary,
                contentDescription = stringResource(R.string.right_arrow_content_description)
            )
        }
    }
    HorizontalDivider(color = MaterialTheme.colorScheme.tertiary)
}
