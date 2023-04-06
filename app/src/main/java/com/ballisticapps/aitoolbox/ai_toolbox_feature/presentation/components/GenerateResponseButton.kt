package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ballisticapps.aitoolbox.R

@Composable
fun GenerateResponseAdButton(
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors = buttonColors(
            backgroundColor = colorResource(id = R.color.lighter_black),
            contentColor = Color.White
        ),

        ) {

        Icon(
            painter = painterResource(id = R.drawable.baseline_smart_display_24),
            contentDescription = "Generate Response Ad Button",
            modifier = Modifier
                .padding(8.dp)
                .size(32.dp),
            tint = Color.White,
        )

        Text(
            "Long Response",
            modifier = Modifier.padding(8.dp),
            color = Color.White,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.weight(1f))
        Text(
            "Watch Ad",
            modifier = Modifier.padding(8.dp),
            color = Color.White,
            fontSize = 18.sp
        )
    }
}

@Composable
fun GenerateResponseButton(
    onClick: () -> Unit,
    icon: Int,
) {
    Button(
        onClick = onClick,
        colors = buttonColors(
            backgroundColor = colorResource(id = R.color.lighter_black),
            contentColor = Color.White
        ),

        ) {

        Icon(
            painter = painterResource(id = icon),
            contentDescription = "ICON",
            modifier = Modifier
                .padding(8.dp)
                .size(32.dp),
            tint = Color.White,
        )

        Text(
            "Short Response",
            modifier = Modifier.padding(8.dp),
            color = Color.White,
            fontSize = 12.sp
        )


        Spacer(modifier = Modifier.weight(1f))

        Text("Free", modifier = Modifier.padding(8.dp), color = Color.White, fontSize = 18.sp)


    }
}

