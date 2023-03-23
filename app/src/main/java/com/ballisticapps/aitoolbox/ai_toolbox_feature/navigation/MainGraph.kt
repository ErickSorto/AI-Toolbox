package com.ballisticapps.aitoolbox.ai_toolbox_feature.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.InterviewMainScreen
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.components.TabLayout
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.ai_page.viewmodel.SharedAIViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun MainGraph(
    navController: NavHostController,
) {
    val sharedAIViewModel: SharedAIViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screens.ToolsMainScreen.route,
    ) {
        //welcome
        composable(route = Screens.ToolsMainScreen.route) {
            InterviewMainScreen(navController = navController)
        }

        composable(
            route = Screens.TabLayoutScreen.route +
                    "?pageRoute={pageRoute}" + "&" + "pageVector={pageVector}",
            arguments = listOf(
                navArgument(
                    name = "pageRoute"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(
                    name = "pageVector"
                ) {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) {
            val pageRoute = it.arguments?.getString("pageRoute") ?: ""
            val vectorIcon = it.arguments?.getInt("pageVector") ?: 0
            TabLayout(pageRoute = pageRoute, vectorIcon = vectorIcon ,sharedAIViewModel)
        }
    }
}