package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.education_page

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
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.HelperEvent
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.components.*
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.education_page.viewmodel.EducationViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EducationSettings(
    pagerState: PagerState,
    viewModel: EducationViewModel = hiltViewModel()
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
        CustomOutlineTextField(
            text = viewModel.subjectOfStudy,
            labelText = "Subject of Study",
            hint = "Enter the subject you are studying",
            isWrapContent = true
        )

        CustomDropdownOutlineTextField(
            text = viewModel.experienceLevel,
            labelText = "Experience Level",
            isExpanded = viewModel.expandedExperienceLevel,
            itemList = viewModel.experienceLevels,
            isEditable = false
        )

        CustomDropdownOutlineTextField(
            text = viewModel.selectedPromptText,
            labelText = "Prompt Type",
            isExpanded = viewModel.expandedPromptType,
            itemList = viewModel.promptTypes
        )

        if (viewModel.selectedPromptText.value == "Study Plan") {
            ChooseDaysOfWeek(daysOfWeek = viewModel.daysOfWeek)
        }

        CustomOutlineTextField(
            text = viewModel.studyGoalText,
            labelText = "Study Goal",
            hint = "Enter your study goal",
            isWrapContent = false,
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