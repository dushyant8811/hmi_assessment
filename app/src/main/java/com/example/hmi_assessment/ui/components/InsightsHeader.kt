package com.example.hmi_assessment.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.em
import com.example.hmi_assessment.ui.theme.dmSansFamily

@Composable
fun InsightsHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomGridIcon(modifier = Modifier.size(28.dp))

        Text(
            text = "Insights",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            fontFamily = dmSansFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 16.sp,
            letterSpacing = (-0.02).em
        )

        Spacer(modifier = Modifier.size(28.dp))
    }
}