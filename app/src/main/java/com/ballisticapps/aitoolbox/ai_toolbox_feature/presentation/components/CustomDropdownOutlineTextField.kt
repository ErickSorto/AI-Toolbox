package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.ballisticapps.aitoolbox.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownOutlineTextField(
    text: MutableState<String>,
    labelText: String,
    isExpanded: MutableState<Boolean>,
    itemList: List<String>,
    isEditable: Boolean = true,
) {
    var dropDownWidth by remember { mutableStateOf(0) }
    Column() {

        OutlinedTextField(
            value = text.value,
            onValueChange = { text.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged {
                    dropDownWidth = it.width
                },
            label = { Text(labelText) },
            trailingIcon = {
                Icon(
                    Icons.Filled.ArrowDropDown,
                    "contentDescription",
                    Modifier.clickable {
                        isExpanded.value =
                            !isExpanded.value
                    })
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                focusedLabelColor = Color.White,
                disabledLabelColor = Color.White,
                focusedTrailingIconColor = Color.White,
                unfocusedTrailingIconColor = Color.White.copy(alpha = 0.7f),
                cursorColor = Color.White,
                focusedPlaceholderColor = Color.White,
                unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                disabledBorderColor = Color.White,
                disabledTrailingIconColor = Color.White.copy(alpha = 0.7f),
            ),
            textStyle = MaterialTheme.typography.labelLarge.copy(color = Color.White),
            enabled = isEditable,
        )
        DropdownMenu(
            expanded = isExpanded.value,
            onDismissRequest = { isExpanded.value = false },
            modifier =
            if (itemList.size * 56.dp >= 300.dp){
                Modifier
                    .width(LocalDensity.current.run { dropDownWidth.toDp() })
                    .height(300.dp)
                    .background(colorResource(id = R.color.lighter_black))
            } else {
                Modifier
                    .width(LocalDensity.current.run { dropDownWidth.toDp() })
                    .wrapContentHeight()
                    .background(colorResource(id = R.color.lighter_black))
            }
        ) {
            itemList.forEach { label ->
                DropdownMenuItem(onClick = {
                    text.value = label
                    isExpanded.value = false
                }, text = { Text(label, color = Color.White) })
            }
        }
    }
}