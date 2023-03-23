package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.interview_page

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.HelperEvent
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.components.BannerAd
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.components.CustomDropdownOutlineTextField
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.components.CustomOutlineTextField
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.components.GenerateResponseAdButton
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.interview_page.viewmodel.InterviewViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun InterviewSettings(
    pagerState: PagerState,
    viewModel: InterviewViewModel = hiltViewModel()
) {
    val activity = LocalContext.current as Activity
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        val fatHeight = 150
        //Level
        CustomDropdownOutlineTextField(
            text = viewModel.selectedLevelText,
            labelText = "Level",
            isExpanded = viewModel.expandedLevel,
            itemList = viewModel.jobLevels
        )

        //Job type
        CustomDropdownOutlineTextField(
            text = viewModel.selectedJobTypeText,
            labelText = "Job Type",
            isExpanded = viewModel.expandedJobType,
            itemList = viewModel.jobTypes
        )

        //specific job
        CustomDropdownOutlineTextField(
            text = viewModel.selectedSpecificJobText,
            labelText = "Specific Job",
            isExpanded = viewModel.expandedSpecificJob,
            itemList = viewModel.specificJobs
        )

        //Enter a list of your skills
        CustomOutlineTextField(
            text = viewModel.skillsText,
            labelText = "Skills",
            hint = "Enter a list of your skills",
            isWrapContent = false
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