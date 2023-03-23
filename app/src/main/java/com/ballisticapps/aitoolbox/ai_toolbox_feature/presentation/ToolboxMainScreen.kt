package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ballisticapps.aitoolbox.R
import com.ballisticapps.aitoolbox.ai_toolbox_feature.navigation.Screens
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.components.BannerAd
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.navigation.Pages
import kotlinx.coroutines.DelicateCoroutinesApi


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(DelicateCoroutinesApi::class, ExperimentalMaterial3Api::class)
@Composable
fun InterviewMainScreen(
    navController: NavHostController
    //  viewModel: InterviewViewModel = hiltViewModel()
) {
    //order alphabetically
    val items = listOf(
        Pages.Interview,
        Pages.Addiction,
        Pages.Diet,
        Pages.Education,
        Pages.Finance,
        Pages.Fitness,
        Pages.Relationship,
        Pages.Travel,
        Pages.Lyrics,
        Pages.SocialMedia
    )

    val itemModifier = Modifier
        .padding(5.dp)
        .background(color = colorResource(id = R.color.lighter_black), RoundedCornerShape(8.dp))
        .aspectRatio(1f)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "AI Toolbox",
                        fontStyle = MaterialTheme.typography.h4.fontStyle,
                        fontWeight = MaterialTheme.typography.h4.fontWeight,
                        fontSize = MaterialTheme.typography.h4.fontSize,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.lighter_black),
                    titleContentColor = Color.White
                )
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .weight(1f),
                    contentPadding = PaddingValues(5.dp),
                ) {
                    itemsIndexed(items) { index, item ->
                        Button(
                            onClick = {
                                navController.navigate(Screens.TabLayoutScreen.route + "?pageRoute=${item.route}" + "&" + "pageVector=${item.vector}")
                            },
                            itemModifier,
                            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.lighter_black))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(5.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                item.vector?.let {
                                    Image(
                                        painter = painterResource(id = it),
                                        contentDescription = null,
                                        modifier = Modifier.size(32.dp),
                                        colorFilter = ColorFilter.tint(Color.White)
                                    )
                                }
                                item.title?.let {
                                    Text(
                                        it,
                                        modifier = Modifier.padding(8.dp),
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
                Box(modifier = Modifier.fillMaxWidth()) {
                    BannerAd(adUnitId = "ca-app-pub-8710979310678386/5365914659")
                }
            }

        }
    )
}

