package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlineTextField(
    text: MutableState<String>,
    labelText: String,
    hint: String,
    isWrapContent: Boolean = false,
    ) {
    Box() {
        OutlinedTextField(
            value = text.value,
            onValueChange = { text.value = it },
            modifier = if (!isWrapContent) {
                Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            } else {
                Modifier
                    .fillMaxWidth()
            },
            label = { Text(labelText) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                focusedLabelColor = Color.White,
                disabledLabelColor = Color.White,
                cursorColor = Color.White,
                focusedPlaceholderColor = Color.White,
                unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
            ),
            textStyle = MaterialTheme.typography.labelLarge.copy(color = Color.White),
        )
        if (text.value.isEmpty() && !isWrapContent) {
            Text(
                text = hint,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center),
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomNumberTextField(
    text: MutableState<String>,
    labelText: String,
    hint: String,
    isWrapContent: Boolean = false,
    maxNumber: Int,
    modifier: Modifier
    ) {
    Box(
        modifier = modifier
    ){
        OutlinedTextField(
            value = text.value,
            onValueChange = { newValue ->
                val newNumber = newValue.toIntOrNull()
                if (newNumber == null) {
                    text.value = ""
                } else if (newNumber in 0..maxNumber) {
                    text.value = newNumber.toString()
                }
            },
            modifier = if (!isWrapContent) {
                Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            } else {
                Modifier.fillMaxWidth()
            },
            label = { Text(labelText) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                focusedLabelColor = Color.White,
                disabledLabelColor = Color.White,
                cursorColor = Color.White,
                focusedPlaceholderColor = Color.White,
                unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
            ),
            textStyle = MaterialTheme.typography.labelLarge.copy(color = Color.White),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { /* Handle Done action if needed */ }
            ),
        )
        if (text.value.isEmpty() && !isWrapContent) {
            Text(
                text = hint,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center),
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}