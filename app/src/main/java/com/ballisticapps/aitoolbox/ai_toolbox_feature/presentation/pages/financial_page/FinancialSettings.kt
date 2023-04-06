package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.financial_page

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
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.financial_page.viewmodel.FinanceViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FinanceSettings(
    pagerState: PagerState,
    viewModel: FinanceViewModel = hiltViewModel()
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
        // Finance goal
        CustomDropdownOutlineTextField(
            text = viewModel.selectedFinanceGoalText,
            labelText = "Finance Goal",
            isExpanded = viewModel.expandedFinanceGoal,
            itemList = viewModel.financeGoals
        )

        // Salary amount
        CustomNumberTextField(
            text = viewModel.salaryAmountText,
            labelText = "Yearly Salary Amount",
            hint = "Enter your salary amount",
            isWrapContent = true,
            500000,
            modifier = Modifier
                .fillMaxWidth()
        )

        // Monthly bill costs
        CustomNumberTextField(
            text = viewModel.billCostsText,
            labelText = "Monthly Bill Costs",
            hint = "Enter your total monthly bill costs",
            isWrapContent = true,
            100000,
            modifier = Modifier
                .fillMaxWidth()
        )

        // Monthly food costs
        CustomNumberTextField(
            text = viewModel.foodCostsText,
            labelText = "Monthly Food Costs",
            hint = "Enter your total monthly food costs",
            isWrapContent = true,
            100000,
            modifier = Modifier
                .fillMaxWidth()
        )

        // Monthly total expenses
        CustomNumberTextField(
            text = viewModel.totalExpensesText,
            labelText = "Monthly Total Expenses",
            hint = "Enter your total monthly expenses",
            isWrapContent = true,
            500000,
            modifier = Modifier
                .fillMaxWidth()
        )

        // Prompt type
        CustomDropdownOutlineTextField(
            text = viewModel.selectedPromptText,
            labelText = "Prompt Type",
            isExpanded = viewModel.promptExpanded,
            itemList = viewModel.promptTypes
        )

        // Finance details
        CustomOutlineTextField(
            text = viewModel.financeDetailsText,
            labelText = "Finance Details",
            hint = "Enter any other details related to your finance goals \n (e.g. income, expenses, debts, etc.)",
            isWrapContent = false
        )

        GenerateResponseButton (
            onClick = {
                scope.launch { pagerState.animateScrollToPage(1) }
                viewModel.onEvent(HelperEvent.ClickGenerateResponseWithoutAdButton(activity)) },
            icon = R.drawable.baseline_attach_money_24
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