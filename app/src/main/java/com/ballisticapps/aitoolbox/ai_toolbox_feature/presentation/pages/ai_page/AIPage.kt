package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.ai_page

import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.components.ArcRotationAnimation
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.ai_page.viewmodel.SharedAIViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AIPage(
    sharedAIViewModel: SharedAIViewModel
) {
    val isLoading = sharedAIViewModel.helperState.value.questionAIExplanation.isLoading
    val aiResponse = sharedAIViewModel.helperState.value.answer

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (isLoading) {
            val infiniteTransition = rememberInfiniteTransition()
            Box(modifier = Modifier.fillMaxSize()){
                Box(modifier = Modifier
                    .align(Alignment.Center)
                    .wrapContentSize()){
                    ArcRotationAnimation(infiniteTransition = infiniteTransition)
                }
            }

        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = aiResponse ?: "No response available.",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                )
            }
        }
    }
}