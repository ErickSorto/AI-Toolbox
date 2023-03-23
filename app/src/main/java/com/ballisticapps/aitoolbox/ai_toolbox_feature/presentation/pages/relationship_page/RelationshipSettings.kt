package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.relationship_page

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
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.components.BannerAd
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.components.CustomDropdownOutlineTextField
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.components.CustomOutlineTextField
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.components.GenerateResponseAdButton
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.relationship_page.viewmodel.RelationshipViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RelationshipSettings(
    pagerState: PagerState,
    viewModel: RelationshipViewModel = hiltViewModel()
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
            text = viewModel.partnerName,
            labelText = "Partner's Name",
            hint = "Enter the name of the person",
            isWrapContent = true
        )

        CustomDropdownOutlineTextField(
            text = viewModel.selectedRelationshipType,
            labelText = "Relationship Type",
            isExpanded = viewModel.expandedRelationshipType,
            itemList = viewModel.relationshipTypes,
            isEditable = false
        )

        CustomDropdownOutlineTextField(
            text = viewModel.selectedUserGender,
            labelText = "Your Gender",
            isExpanded = viewModel.expandedUserGender,
            itemList = viewModel.userGenders,
            isEditable = false
        )

        CustomDropdownOutlineTextField(
            text = viewModel.selectedPartnerGender,
            labelText = "Partner's Gender",
            isExpanded = viewModel.expandedPartnerGender,
            itemList = viewModel.partnerGenders,
            isEditable = false
        )

        CustomDropdownOutlineTextField(
            text = viewModel.selectedPromptText,
            labelText = "Prompt Type",
            isExpanded = viewModel.expandedPromptType,
            itemList = viewModel.promptTypes
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