package com.ballisticapps.aitoolbox.ai_toolbox_feature.navigation

sealed class Screens(
    val route: String,
    val title: String?= null,
) {
    object ToolsMainScreen: Screens(
        route = "tools_screen",
        title = "Home",
    )
    object TabLayoutScreen: Screens(
        route = "tab_layout_screen",
        title = "Tab Layout",
    )

}