package com.example.hmi_assessment.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hmi_assessment.ui.theme.dmSansFamily

@Composable
fun StabilityChart(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {

        Text(
            text = "Based on your recent logs and symptom\npatterns.",
            color = Color(0xFF696770),
            fontFamily = dmSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 14.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Stability Score",
            fontFamily = dmSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.sp,
            color = Color.Black
        )

        Text(
            text = "78%",
            fontFamily = dmSansFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            lineHeight = 18.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // --- The Custom Canvas Chart ---
        val textMeasurer = rememberTextMeasurer()

        val lightPurpleRight = Color(0xFFE6E0FB)
        val darkPurpleRight = Color(0xFFB4A8DA)
        val lightPurpleLeft = Color(0xFFD8CCFE)
        val darkPurpleLeft = Color(0xFFBCA7FD)

        val accentGreen = Color(0xFF6E8C82)
        val activeTextColor = Color.Black
        val inactiveTextColor = Color(0xFF696770)

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            val paddingBottom = 40.dp.toPx()
            val paddingStart = 40.dp.toPx()
            val paddingEnd = 20.dp.toPx()

            val chartHeight = canvasHeight - paddingBottom

            val labelStartX = paddingStart
            val labelEndX = canvasWidth - paddingEnd
            val labelWidth = labelEndX - labelStartX

            val graphStartX = labelStartX - 20.dp.toPx()
            val graphEndX = labelEndX + 20.dp.toPx()
            val graphWidth = graphEndX - graphStartX

            val xLabels = listOf("Jan", "Feb", "Mar", "Apr")
            val xStep = labelWidth / (xLabels.size - 1)
            val marchX = labelStartX + (2 * xStep)

            val lightPath = Path().apply {
                moveTo(graphStartX, chartHeight)
                cubicTo(
                    graphStartX + graphWidth * 0.4f, chartHeight * 0.95f,
                    graphStartX + graphWidth * 0.7f, chartHeight * 0.60f,
                    graphStartX + graphWidth, chartHeight * 0.30f
                )
                lineTo(graphStartX + graphWidth, chartHeight)
                close()
            }

            val darkPath = Path().apply {
                moveTo(graphStartX, chartHeight)
                cubicTo(
                    graphStartX + graphWidth * 0.4f, chartHeight * 0.98f,
                    graphStartX + graphWidth * 0.7f, chartHeight * 0.75f,
                    graphStartX + graphWidth, chartHeight * 0.60f
                )
                lineTo(graphStartX + graphWidth, chartHeight * 0.80f)
                cubicTo(
                    graphStartX + graphWidth * 0.7f, chartHeight * 0.88f,
                    graphStartX + graphWidth * 0.4f, chartHeight * 0.98f,
                    graphStartX, chartHeight
                )
                close()
            }

            clipRect(right = marchX) {
                drawPath(path = lightPath, color = lightPurpleLeft)
                drawPath(path = darkPath, color = darkPurpleLeft)
            }
            clipRect(left = marchX) {
                drawPath(path = lightPath, color = lightPurpleRight)
                drawPath(path = darkPath, color = darkPurpleRight)
            }

            val dayTextStyle = TextStyle(
                color = Color.Black,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 11.5.sp,
                letterSpacing = 0.sp,
                fontFamily = dmSansFamily
            )

            val yLabels = listOf("24d", "28d", "32d")
            val yStepSize = chartHeight / 3
            yLabels.forEachIndexed { index, label ->
                val yPos = chartHeight - (index * yStepSize) - 5.dp.toPx()
                drawText(
                    textMeasurer = textMeasurer,
                    text = label,
                    style = dayTextStyle,
                    topLeft = Offset(0f, yPos)
                )
            }

            xLabels.forEachIndexed { index, label ->
                val isHighlighted = label == "Mar"

                val dynamicMonthStyle = TextStyle(
                    color = if (isHighlighted) activeTextColor else inactiveTextColor,
                    fontSize = 12.sp,
                    fontWeight = if (isHighlighted) FontWeight.Medium else FontWeight.Normal,
                    lineHeight = 11.5.sp,
                    letterSpacing = 0.sp,
                    fontFamily = dmSansFamily
                )

                val xPos = labelStartX + (index * xStep) - 10.dp.toPx()
                drawText(
                    textMeasurer = textMeasurer,
                    text = label,
                    style = dynamicMonthStyle,
                    topLeft = Offset(xPos, canvasHeight - 20.dp.toPx())
                )
            }

            val marchY = chartHeight - (1.5f * yStepSize)

            drawLine(
                color = accentGreen,
                start = Offset(marchX, chartHeight),
                end = Offset(marchX, marchY),
                strokeWidth = 3f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )

            drawCircle(
                color = accentGreen,
                radius = 6.dp.toPx(),
                center = Offset(marchX, marchY)
            )

            val tooltipWidth = 100.dp.toPx()
            val tooltipHeight = 40.dp.toPx()
            val tooltipX = marchX - (tooltipWidth / 2)
            val tooltipY = marchY - tooltipHeight - 15.dp.toPx()

            drawRoundRect(
                color = Color.Black,
                topLeft = Offset(tooltipX, tooltipY),
                size = Size(tooltipWidth, tooltipHeight),
                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
            )

            val pointerPath = Path().apply {
                moveTo(marchX - 5.dp.toPx(), tooltipY + tooltipHeight)
                lineTo(marchX + 5.dp.toPx(), tooltipY + tooltipHeight)
                lineTo(marchX, tooltipY + tooltipHeight + 5.dp.toPx())
                close()
            }
            drawPath(path = pointerPath, color = Color.Black)

            val tooltipTextLayout = textMeasurer.measure(
                text = "Stability\nImproving",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 18.sp,
                    fontFamily = dmSansFamily,
                    textAlign = TextAlign.Center
                )
            )

            drawText(
                textLayoutResult = tooltipTextLayout,
                topLeft = Offset(
                    tooltipX + (tooltipWidth - tooltipTextLayout.size.width) / 2,
                    tooltipY + (tooltipHeight - tooltipTextLayout.size.height) / 2
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StabilityChartPreview() {
    InsightsCard(title = "Stability Summary", modifier = Modifier.padding(16.dp)) {
        StabilityChart()
    }
}