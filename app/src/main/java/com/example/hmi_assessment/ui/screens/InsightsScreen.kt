package com.example.hmi_assessment.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

import com.example.hmi_assessment.ui.theme.AppBackground

import com.example.hmi_assessment.ui.components.InsightsHeader
import com.example.hmi_assessment.ui.components.InsightsCard
import com.example.hmi_assessment.ui.components.CycleTrendsChart
import com.example.hmi_assessment.ui.components.BodyMetabolicChart
import com.example.hmi_assessment.ui.components.BodySignalsChart
import com.example.hmi_assessment.ui.components.LifestyleImpactChart
import com.example.hmi_assessment.ui.components.StabilityChart
import com.example.hmi_assessment.ui.components.CustomFloatingFooter

@Composable
fun InsightsScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = AppBackground,

        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(top = innerPadding.calculateTopPadding())
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                InsightsHeader()

                InsightsCard(title = "Stability Summary") {
                    StabilityChart()
                }

                InsightsCard(title = "Cycle Trends") {
                    CycleTrendsChart()
                }

                InsightsCard(title = "Body & Metabolic Trends") {
                    BodyMetabolicChart()
                }

                InsightsCard(title = "Body Signals") {
                    BodySignalsChart()
                }

                InsightsCard(title = "Lifestyle Impact") {
                    LifestyleImpactChart()
                }

                Spacer(modifier = Modifier.height(120.dp))
            }
        }

        // Footer floats on top of content
        CustomFloatingFooter(
            currentRoute = "Insights",
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InsightsScreenPreview() {
    InsightsScreen()
}