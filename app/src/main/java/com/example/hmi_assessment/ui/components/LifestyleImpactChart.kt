package com.example.hmi_assessment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.em
import com.example.hmi_assessment.ui.theme.dmSansFamily

data class LifestyleData(
    val name: String,
    val score: Int,
    val baseColor: Color,
    val endAlpha: Float
)

@Composable
fun LifestyleImpactChart(modifier: Modifier = Modifier) {

    val lifestyleDataList = listOf(
        LifestyleData("Sleep", 6, Color(0xFFB4A8DA), 0.34f),
        LifestyleData("Hydrate", 3, Color(0xFFE99597), 0.56f),
        LifestyleData("Caffeine", 5, Color(0xFF6E8C82), 0.38f),
        LifestyleData("Exercise", 4, Color(0xFFF5C3C4), 0.54f)
    )

    Column(modifier = modifier.fillMaxWidth()) {

        // Inner Card Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Correlation Strength",
                fontFamily = dmSansFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                lineHeight = 16.sp,
                letterSpacing = (-0.02).em,
                color = Color.Black
            )

            // Dropdown Box
            Box(
                modifier = Modifier
                    .width(72.dp)
                    .height(24.dp)
                    .background(Color(0xFFF7F6F6), RoundedCornerShape(6.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "4 months",
                        fontFamily = dmSansFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp,
                        color = Color(0xFF696770)
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color(0xFF696770),
                        modifier = Modifier
                            .size(14.dp)
                            .padding(start = 2.dp)
                    )
                }
            }
        }

        // --- Grid Rows ---
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            lifestyleDataList.forEach { data ->
                CategoryRow(data = data)
            }
        }
    }
}

@Composable
private fun CategoryRow(data: LifestyleData) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = data.name,
            fontFamily = dmSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 8.sp,
            color = Color.Black,
            modifier = Modifier.width(42.dp)
        )

        // The 9 Pills Container
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            for (i in 0 until 9) {
                Pill(
                    isActive = i < data.score,
                    baseColor = data.baseColor,
                    endAlpha = data.endAlpha,
                    // FIX: Pass down whether this specific pill is at an even or odd index
                    isEvenIndex = i % 2 == 0,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun Pill(
    isActive: Boolean,
    baseColor: Color,
    endAlpha: Float,
    isEvenIndex: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(22.dp)
            .background(
                brush = if (isActive) {
                    // FIX: Alternate the gradient colors based on the index!
                    val gradientColors = if (isEvenIndex) {
                        // Left is solid, Right is faded
                        listOf(baseColor, baseColor.copy(alpha = endAlpha))
                    } else {
                        // Left is faded, Right is solid
                        listOf(baseColor.copy(alpha = endAlpha), baseColor)
                    }
                    Brush.horizontalGradient(colors = gradientColors)
                } else {
                    SolidColor(Color(0xFFEDEBEB))
                },
                shape = RoundedCornerShape(4.dp)
            )
    )
}

@Preview(showBackground = true)
@Composable
fun LifestyleImpactChartPreview() {
    InsightsCard(title = "Lifestyle Impact", modifier = Modifier.padding(16.dp)) {
        LifestyleImpactChart()
    }
}