package com.example.hmi_assessment.ui.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.em
import com.example.hmi_assessment.ui.theme.dmSansFamily
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.PI

data class SymptomData(
    val name: String,
    val percentage: Int,
    val lightColor: Color,
    val darkColor: Color
)

@Composable
fun BodySignalsChart(modifier: Modifier = Modifier) {

    val chartData = listOf(
        SymptomData("Bloating", 31, Color(0xFFF5F2FF), Color(0xFFB4A8DA)),
        SymptomData("Fatigue", 21, Color(0xFFFFE4E4), Color(0xFFE99597)),
        SymptomData("Acne", 17, Color(0xFFECFFF9), Color(0xFF6E8C82)),
        SymptomData("Mood", 30, Color(0xFFFFF1F1), Color(0xFFF4C3C4))
    )

    val totalPercentage = chartData.sumOf { it.percentage }.toFloat()

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Symptom Trends",
            fontFamily = dmSansFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 16.sp,
            letterSpacing = (-0.02).em,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Compared to last cycle",
            fontFamily = dmSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 14.sp,
            letterSpacing = 0.em,
            color = Color(0xFF696770),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // The Donut Chart
        val textMeasurer = rememberTextMeasurer()

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(4.dp)
        ) {
            val strokeWidth = 50.dp.toPx()

            val arcRadius = (size.minDimension / 2f) - (strokeWidth / 2f)
            var currentStartAngle = 270f
            val labelAngles = mutableListOf<Float>()

            // RINGS
            chartData.forEach { item ->
                val sweepAngle = (item.percentage / totalPercentage) * 360f
                labelAngles.add(currentStartAngle + (sweepAngle / 2f))

                withTransform({
                    rotate(degrees = currentStartAngle, pivot = center)
                }) {
                    val brush = Brush.sweepGradient(
                        0.0f to item.darkColor,
                        (sweepAngle / 360f) to item.lightColor,
                        1.0f to item.lightColor,
                        center = center
                    )

                    drawArc(
                        brush = brush,
                        startAngle = 0f,
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        topLeft = Offset(center.x - arcRadius, center.y - arcRadius),
                        size = Size(arcRadius * 2, arcRadius * 2),
                        style = Stroke(width = strokeWidth)
                    )
                }
                currentStartAngle += sweepAngle
            }

            // BADGES
            val badgeRadius = arcRadius + (strokeWidth / 2f)

            chartData.forEachIndexed { index, item ->
                val angleInRadians = labelAngles[index] * (PI / 180f)

                val labelX = center.x + cos(angleInRadians).toFloat() * badgeRadius
                val labelY = center.y + sin(angleInRadians).toFloat() * badgeRadius

                val circleRadius = 30.dp.toPx()

                drawIntoCanvas { canvas ->
                    val shadowPaint = Paint().apply {
                        isAntiAlias = true
                        color = android.graphics.Color.WHITE
                        setShadowLayer(
                            35f,
                            0f,
                            8f,
                            android.graphics.Color.argb(45, 0, 0, 0)
                        )
                    }
                    canvas.nativeCanvas.drawCircle(
                        labelX,
                        labelY,
                        circleRadius,
                        shadowPaint
                    )
                }

                val pctText = "${item.percentage}%"
                val nameText = item.name

                val pctLayout = textMeasurer.measure(
                    text = pctText,
                    style = TextStyle(
                        fontFamily = dmSansFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.65.sp,
                        lineHeight = 13.65.sp,
                        letterSpacing = 0.em,
                        color = Color.Black
                    )
                )
                val nameLayout = textMeasurer.measure(
                    text = nameText,
                    style = TextStyle(
                        fontFamily = dmSansFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 11.37.sp,
                        lineHeight = 11.37.sp,
                        letterSpacing = 0.em,
                        color = Color.Black
                    )
                )

                val textGap = 2.dp.toPx()
                val totalTextHeight = pctLayout.size.height + nameLayout.size.height + textGap
                val startY = labelY - (totalTextHeight / 2f)

                drawText(
                    textLayoutResult = pctLayout,
                    topLeft = Offset(
                        x = labelX - (pctLayout.size.width / 2f),
                        y = startY
                    )
                )

                drawText(
                    textLayoutResult = nameLayout,
                    topLeft = Offset(
                        x = labelX - (nameLayout.size.width / 2f),
                        y = startY + pctLayout.size.height + textGap
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BodySignalsChartPreview() {
    InsightsCard(title = "Body Signals", modifier = Modifier.padding(16.dp)) {
        BodySignalsChart()
    }
}