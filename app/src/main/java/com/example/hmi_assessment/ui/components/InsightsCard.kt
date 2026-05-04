package com.example.hmi_assessment.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.hmi_assessment.ui.theme.dmSansFamily

@Composable
fun InsightsCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    ) {
        Text(
            text = title,
            fontFamily = dmSansFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 16.sp,
            letterSpacing = (-0.02).em,
            color = Color(0xFF1A1A1A),
            modifier = Modifier.padding(bottom = 12.dp, start = 4.dp)
        )

        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFFFFFFF),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 6.dp,
                    shape = RoundedCornerShape(12.dp),
                    clip = false,
                    ambientColor = Color(0xFF0D0A2C).copy(alpha = 0.12f),
                    spotColor = Color(0xFF0D0A2C).copy(alpha = 0.35f)
                ),
        ) {
            Box(modifier = Modifier.padding(20.dp)) {
                content()
            }
        }
    }
}