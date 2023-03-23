package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Tab


import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier

import androidx.compose.ui.UiComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ballisticapps.aitoolbox.R
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.ai_page.AIPage
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.addiction_page.AddictionSettings
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.ai_page.viewmodel.SharedAIViewModel
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.ai_page.viewmodel.UiEvent
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.diet_page.DietSettings
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.education_page.EducationSettings
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.financial_page.FinanceSettings
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.fitness_page.FitnessSettings
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.interview_page.InterviewSettings
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.lyrics_page.LyricsSettings
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.navigation.Pages
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.relationship_page.RelationshipSettings
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.socialmedia_page.SocialMediaSettings
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.travel_page.TravelSettings
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager

import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPagerApi::class)
@Composable
@UiComposable
fun TabLayout(
    pageRoute: String,
    vectorIcon: Int,
    sharedAIViewModel: SharedAIViewModel,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val pages = listOf("Settings", "AI")
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()




    LaunchedEffect(key1 = true) {
        sharedAIViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }


    //create tab layout with 3 tabs, one for dream title and content, one for dream information and one for dream artificial intelligence
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.Transparent,
    ) {
        it
        Column() {
            TabRow(
                modifier = Modifier,
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        // custom indicator
                        Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                        color = Color.White,
                    )
                },
                contentColor = Color.Black,
                containerColor = colorResource(id = R.color.lighter_black),
            ) {
                pages.forEachIndexed { index, page ->
                    Tab(
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(page, style = typography.h5)
                                if (page == "Settings") {
                                    Icon(
                                        painter = painterResource(id = vectorIcon),
                                        contentDescription = "Vector Icon",
                                        tint =if (pagerState.currentPage == index) Color.White else Color.White.copy(alpha = 0.5f),
                                        modifier = Modifier
                                            .size(32.dp).padding(start = 8.dp)
                                    )
                                }
                            }
                        },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch { pagerState.animateScrollToPage(index) }
                        },
                    )
                }
            }
            HorizontalPager(
                count = pages.size,
                state = pagerState,
            ) { page ->
                when (page) {
                    0 -> {
                        when (pageRoute) {
                            Pages.Interview.route -> {
                                InterviewSettings(pagerState = pagerState)
                            }
                            Pages.Diet.route -> {
                                DietSettings(pagerState = pagerState)
                            }
                            Pages.Fitness.route -> {
                                FitnessSettings(pagerState = pagerState)
                            }
                            Pages.Addiction.route -> {
                                AddictionSettings(pagerState = pagerState)
                            }
                            Pages.Finance.route -> {
                                FinanceSettings(pagerState = pagerState)
                            }
                            Pages.Education.route -> {
                                EducationSettings(pagerState = pagerState)
                            }
                            Pages.Relationship.route -> {
                                RelationshipSettings(pagerState = pagerState)
                            }
                            Pages.Travel.route -> {
                                TravelSettings(pagerState = pagerState)
                            }
                            Pages.Lyrics.route -> {
                                LyricsSettings(pagerState = pagerState)
                            }
                            Pages.SocialMedia.route -> {
                                SocialMediaSettings(pagerState)
                            }
                        }
                    }
                    1 -> {
                        AIPage(sharedAIViewModel = sharedAIViewModel)
                    }
                }
            }
        }
    }

}