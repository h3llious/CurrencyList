package com.nhatbui.currency.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.nhatbui.currency.ui.R

@Composable
internal fun SearchBar(
    onBackAction: () -> Unit,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var input by remember { mutableStateOf(TextFieldValue("")) }
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back_content_description),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clip(CircleShape)
                .size(36.dp)
                .clickable { onBackAction() }
                .padding(6.dp)
        )
        SearchTextField(
            input = input,
            onValueChange = { newValue ->
                input = newValue
                onValueChange(newValue.text)
            }
        )
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = stringResource(R.string.clear_content_description),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clip(CircleShape)
                .size(36.dp)
                .clickable {
                    if (input.text.isEmpty()) {
                        onBackAction()
                    } else {
                        input = TextFieldValue("")
                        onValueChange(input.text)
                    }
                }
                .padding(6.dp)
        )
    }
}

@Composable
private fun RowScope.SearchTextField(
    input: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    val textFieldFocusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        textFieldFocusRequester.requestFocus()
    }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(textFieldFocusRequester)
            .weight(1f),
        value = input,
        onValueChange = { onValueChange(it) },
        singleLine = true,
        placeholder = {
            Text(
                text = stringResource(R.string.search_bar_placeholder),
                color = MaterialTheme.colorScheme.tertiary
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        )

    )
}
