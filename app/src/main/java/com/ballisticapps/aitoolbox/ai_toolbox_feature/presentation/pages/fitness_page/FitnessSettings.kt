package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.fitness_page

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
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.fitness_page.viewmodel.FitnessViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FitnessSettings(
    pagerState: PagerState,
    viewModel: FitnessViewModel = hiltViewModel()
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
        // Age
        CustomNumberTextField(
            text = viewModel.ageText,
            labelText = "Age",
            hint = "",
            isWrapContent = true,
            100,
            modifier = Modifier
                .fillMaxWidth()
        )

        // Metric or Imperial
        CustomDropdownOutlineTextField(
            text = viewModel.metricType,
            labelText = "Measurement System",
            isExpanded = viewModel.metricSystemExpanded,
            itemList = viewModel.metricSystems,
            isEditable = false
        )

        if (viewModel.metricType.value == "Imperial") {
            CustomNumberTextField(
                text = viewModel.weightImperialText,
                labelText = "Weight (in lbs)",
                hint = "",
                isWrapContent = true,
                500,
                modifier = Modifier
                    .fillMaxWidth()
            )
            // Height (ft and in)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 0.dp, 0.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CustomNumberTextField(
                    text = viewModel.heightFeetImperialText,
                    labelText = "Height (ft)",
                    hint = "",
                    isWrapContent = true,
                    8,
                    modifier = Modifier
                        .weight(.5f)
                )
                CustomNumberTextField(
                    text = viewModel.heightInchImperialText,
                    labelText = "Height (in)",
                    hint = "",
                    isWrapContent = true,
                    11,
                    modifier = Modifier
                        .weight(.5f)
                )

            }
        } else {
            // Weight
            CustomNumberTextField(
                text = viewModel.weightMetricText,
                labelText = "Weight (in kg)",
                hint = "Enter your weight",
                isWrapContent = true,
                200,
                modifier = Modifier
                    .fillMaxWidth()
            )

            // Height
            CustomNumberTextField(
                text = viewModel.heightMetricText,
                labelText = "Height (in cm)",
                hint = "Enter your height",
                isWrapContent = true,
                245,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        // Experience level
        CustomDropdownOutlineTextField(
            text = viewModel.selectedExperienceLevelText,
            labelText = "Experience Level",
            isExpanded = viewModel.experienceLevelExpanded,
            itemList = viewModel.experienceLevels,
            isEditable = false
        )

        // Workout frequency
        CustomDropdownOutlineTextField(
            text = viewModel.selectedWorkoutFrequencyText,
            labelText = "Desired Workout Frequency",
            isExpanded = viewModel.workoutFrequencyExpanded,
            itemList = viewModel.workoutFrequencies,
            isEditable = false
        )

        // Workout type
        CustomDropdownOutlineTextField(
            text = viewModel.selectedWorkoutTypeText,
            labelText = "Workout Type",
            isExpanded = viewModel.workoutTypeExpanded,
            itemList = viewModel.workoutTypes,
            isEditable = true
        )

        // Fitness goal
        CustomDropdownOutlineTextField(
            text = viewModel.selectedFitnessGoalText,
            labelText = "Fitness Goal",
            isExpanded = viewModel.fitnessGoalExpanded,
            itemList = viewModel.fitnessGoals,
            isEditable = true
        )

        // Fitness prompt
        CustomDropdownOutlineTextField(
            text = viewModel.selectedPromptText,
            labelText = "Prompt Type",
            isExpanded = viewModel.promptExpanded,
            itemList = viewModel.promptTypes
        )

        if (viewModel.selectedPromptText.value == "Workout Plan") {
            ChooseDaysOfWeek(daysOfWeek = viewModel.daysOfWeek)
        }

        // Enter any other fitness-related details
        CustomOutlineTextField(
            text = viewModel.detailsText,
            labelText = "Other Details",
            hint = "Enter any other details related to your fitness (e.g. injuries, preferences, etc.)",
            isWrapContent = false
        )

        GenerateResponseButton (
            onClick = {
                scope.launch { pagerState.animateScrollToPage(1) }
                viewModel.onEvent(HelperEvent.ClickGenerateResponseWithoutAdButton(activity)) },
            icon = R.drawable.baseline_directions_run_24
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
