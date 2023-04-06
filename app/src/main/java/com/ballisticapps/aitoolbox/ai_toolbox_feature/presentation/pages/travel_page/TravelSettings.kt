package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.travel_page

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
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.travel_page.viewmodel.TravelViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TravelSettings(
    pagerState: PagerState,
    viewModel: TravelViewModel = hiltViewModel()
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
        CustomDropdownOutlineTextField(
            text = viewModel.selectedTravelDestination,
            labelText = "Destination",
            isExpanded = viewModel.expandedTravelDestination,
            itemList = viewModel.travelDestinations,
            isEditable = true
        )

        CustomNumberTextField(
            text = viewModel.stayingDuration,
            labelText = "Duration (in days)",
            hint = "Enter duration",
            isWrapContent = true,
            365,
            modifier = Modifier
                .fillMaxWidth()
        )

        CustomNumberTextField(
            text = viewModel.travelBudget,
            labelText = "Budget (in USD)",
            hint = "Enter your budget",
            isWrapContent = true,
            1000000,
            modifier = Modifier
                .fillMaxWidth()
        )

        CustomNumberTextField(
            text = viewModel.numberOfPeople,
            labelText = "Number of People",
            hint = "Enter the number of people",
            isWrapContent = true,
            100,
            modifier = Modifier
                .fillMaxWidth()
        )

        CustomDropdownOutlineTextField(
            text = viewModel.selectedTravelPrompt,
            labelText = "Prompt Type",
            isExpanded = viewModel.expandedTravelPrompt,
            itemList = viewModel.travelPrompts,
            isEditable = false
        )

        GenerateResponseButton (
            onClick = {
                scope.launch { pagerState.animateScrollToPage(1) }
                viewModel.onEvent(HelperEvent.ClickGenerateResponseWithoutAdButton(activity)) },
            icon = R.drawable.baseline_travel_explore_24
        )

        GenerateResponseAdButton(
            onClick = {
                scope.launch { pagerState.animateScrollToPage(1) }
                viewModel.onEvent(HelperEvent.ClickGenerateResponseButton(activity))
            }
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            BannerAd(adUnitId = "ca-app-pub-8710979310678386/5365914659")
        }
    }
}