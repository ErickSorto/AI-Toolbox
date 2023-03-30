package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.book_summary_page

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.book_summary_page.viewmodel.BookSummaryViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BookSummarySettings(
    pagerState: PagerState,
    viewModel: BookSummaryViewModel = hiltViewModel()
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
        // Book Title
        CustomOutlineTextField(
            text = viewModel.bookTitleText,
            labelText = "Book Title",
            hint = "",
            isWrapContent = true
        )

        // Author Name
        CustomOutlineTextField(
            text = viewModel.authorNameText,
            labelText = "Author Name",
            hint = "",
            isWrapContent = true
        )

        // Chapter (optional)
        CustomOutlineTextField(
            text = viewModel.chapterText,
            labelText = "Chapter (optional)",
            hint = "",
            isWrapContent = true
        )

        // Prompt Type
        CustomDropdownOutlineTextField(
            text = viewModel.selectedPromptText,
            labelText = "Prompt Type",
            isExpanded = viewModel.promptExpanded,
            itemList = viewModel.promptTypes,
        )

        // Generate Response Button
        GenerateResponseAdButton(
            onClick = {
                scope.launch { pagerState.animateScrollToPage(1) }
                viewModel.onEvent(HelperEvent.ClickGenerateResponseButton(activity))
            }
        )

        // Ad Banner
        Box(modifier = Modifier.fillMaxWidth()) {
            BannerAd(adUnitId = "ca-app-pub-8710979310678386/5365914659")
        }
    }
}