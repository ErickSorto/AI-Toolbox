package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.components



import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.ballisticapps.aitoolbox.R
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.diet_page.viewmodel.DaySelection

@Composable
fun ChooseDaysOfWeek(
    daysOfWeek: List<DaySelection>
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Choose Days of Week")
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 8.dp)
        ) {
            daysOfWeek.forEach { daySelection ->
                val isSelected = daySelection.isSelected.value
                Button(
                    onClick = { daySelection.isSelected.value = !isSelected },
                    colors = buttonColors(
                        backgroundColor = if (isSelected) colorResource(id = R.color.lighter_black) else Color.Black.copy(
                        ),
                        contentColor = if (isSelected) Color.White else Color.White.copy(alpha = 0.5f)
                    ),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = daySelection.day.take(2),
                        color = if (isSelected) Color.White else Color.White.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}
