package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.lyrics_page

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
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.lyrics_page.viewmodel.LyricsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LyricsSettings(
    pagerState: PagerState,
    viewModel: LyricsViewModel = hiltViewModel()
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
            text = viewModel.title,
            labelText = "Title",
            hint = "Enter the title of your song",
            isWrapContent = true
        )

        CustomDropdownOutlineTextField(
            text = viewModel.selectedGenre,
            labelText = "Genre",
            isExpanded = viewModel.expandedGenre,
            itemList = viewModel.genres,
            isEditable = false
        )

        CustomDropdownOutlineTextField(
            text = viewModel.selectedRhymeScheme,
            labelText = "Rhyme Scheme",
            isExpanded = viewModel.expandedRhymeScheme,
            itemList = viewModel.rhymeSchemes,
            isEditable = false
        )

        CustomDropdownOutlineTextField(
            text = viewModel.selectedLength,
            labelText = "Length",
            isExpanded = viewModel.expandedLength,
            itemList = viewModel.lengths,
            isEditable = false
        )

        CustomOutlineTextField(
            text = viewModel.additionalDetails,
            labelText = "Additional Details",
            hint = "Enter any additional details related to your lyrics",
            isWrapContent = false
        )

        GenerateResponseButton (
            onClick = {
                scope.launch { pagerState.animateScrollToPage(1) }
                viewModel.onEvent(HelperEvent.ClickGenerateResponseWithoutAdButton(activity)) },
            icon = R.drawable.baseline_music_note_24
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