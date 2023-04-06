package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.addiction_page

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ballisticapps.aitoolbox.R
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.HelperEvent
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.components.*
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.addiction_page.viewmodel.AddictionViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddictionSettings(
    pagerState: PagerState,
    viewModel: AddictionViewModel = hiltViewModel()
) {
    val activity = LocalContext.current as Activity

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),

        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Addiction type
        CustomDropdownOutlineTextField(
            text = viewModel.selectedAddictionTypeText,
            labelText = "Addiction Type",
            isExpanded = viewModel.expandedAddictionType,
            itemList = viewModel.addictionTypes
        )

        // Prompt type
        CustomDropdownOutlineTextField(
            text = viewModel.selectedPromptText,
            labelText = "Prompt Type",
            isExpanded = viewModel.promptExpanded,
            itemList = viewModel.promptTypes
        )

        // Addiction details
        CustomOutlineTextField(
            text = viewModel.addictionDetailsText,
            labelText = "Addiction Details",
            hint = "Enter any other details related to your addiction \n (e.g. frequency, triggers, etc.)",
            isWrapContent = false
        )

        GenerateResponseButton (
            onClick = {
                scope.launch { pagerState.animateScrollToPage(1) }
                viewModel.onEvent(HelperEvent.ClickGenerateResponseWithoutAdButton(activity)) },
            icon = R.drawable.baseline_healing_24
        )


        GenerateResponseAdButton(
            onClick = {
                scope.launch { pagerState.animateScrollToPage(1) }
                viewModel.onEvent(HelperEvent.ClickGenerateResponseButton(activity)) }
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            BannerAd(adUnitId = "ca-app-pub-8710979310678386/5365914659")
        }
    }
}