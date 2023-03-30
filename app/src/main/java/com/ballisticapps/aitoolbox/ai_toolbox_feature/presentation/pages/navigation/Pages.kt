package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.navigation

import com.ballisticapps.aitoolbox.R

sealed class Pages(
    val route: String? = null,
    val title: String?= null,
    val vector: Int?= null,
) {

    object BookSummary: Pages(
        route = "book_summary_page",
        title = "Book Summary",
        vector = R.drawable.baseline_local_library_24
    )
    object Interview: Pages(
        route = "interview_page",
        title = "Interview",
        vector = R.drawable.baseline_speaker_notes_24
    )

    object Fitness: Pages(
        route = "fitness_page",
        title = "Fitness",
        vector = R.drawable.baseline_directions_run_24
    )

    object Diet: Pages(
        route = "diet_page",
        title = "Diet",
        vector = R.drawable.baseline_fastfood_24
    )

    object Addiction: Pages(
        route = "addiction_page",
        title = "Addiction",
        vector = R.drawable.baseline_healing_24
    )

    object Finance: Pages(
        route = "finance_page",
        title = "Finance",
        vector = R.drawable.baseline_attach_money_24
    )

    object Travel: Pages(
        route = "travel_page",
        title = "Travel",
        vector = R.drawable.baseline_travel_explore_24
    )

    object Education: Pages(
        route = "education_page",
        title = "Education",
        vector = R.drawable.baseline_edit_24
    )

    object Relationship: Pages(
        route = "relationship_page",
        title = "Relationship",
        vector = R.drawable.baseline_group_24
    )

    object SocialMedia: Pages(
        route = "social_media_page",
        title = "Social Media",
        vector = R.drawable.baseline_app_registration_24
    )

    object Lyrics: Pages(
        route = "lyrics_page",
        title = "Lyrics",
        vector = R.drawable.baseline_music_note_24
    )

}