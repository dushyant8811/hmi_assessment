package com.example.hmi_assessment.ui.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.em
import com.example.hmi_assessment.ui.theme.dmSansFamily

@Composable
fun BodyMetabolicChart(modifier: Modifier = Modifier) {
    var isMonthly by remember { mutableStateOf(true) }

    val lineColor = Color(0xFFE99597)

    val fillColorTop = Color(0xFFD27A88).copy(alpha = 0.46f)
    val fillColorBottom = Color(0xFFD27A88).copy(alpha = 0.0f)

    val dashedLineColor = Color(0xFFE0DDDD).copy(alpha = 0.60f)

    val monthlyData = listOf(
        Pair("Jan", 32f),
        Pair("Feb", 45f),
        Pair("Mar", 38f),
        Pair("Apr", 70f),
        Pair("May", 55f),
    )

    val weeklyData = listOf(
        Pair("W1", 35f),
        Pair("W2", 40f),
        Pair("W3", 45f),
        Pair("W4", 42f),
        Pair("W5", 50f),
    )

    val data = if (isMonthly) monthlyData else weeklyData

    Column(modifier = modifier.fillMaxWidth()) {

        // Header Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column {
                Text(
                    text = "Your weight",
                    fontFamily = dmSansFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    letterSpacing = 0.em,
                    color = Color.Black
                )
                Text(
                    text = "in kg",
                    fontFamily = dmSansFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    letterSpacing = 0.em,
                    color = Color(0xFF696770)
                )
            }

            // Toggle Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ToggleButton(
                    label = "Monthly",
                    isActive = isMonthly,
                    onClick = { isMonthly = true }
                )
                ToggleButton(
                    label = "Weekly",
                    isActive = !isMonthly,
                    onClick = { isMonthly = false }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Chart
        val textMeasurer = rememberTextMeasurer()

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            val paddingStart = 24.dp.toPx()
            val paddingBottom = 24.dp.toPx()
            val paddingEnd = 16.dp.toPx()

            val chartLeft = paddingStart
            val chartRight = canvasWidth - paddingEnd
            val chartTop = 0f
            val chartBottom = canvasHeight - paddingBottom
            val chartWidth = chartRight - chartLeft
            val chartHeight = chartBottom - chartTop

            val minValue = 25f
            val maxValue = 75f
            val valueRange = maxValue - minValue

            fun valueToY(value: Float): Float {
                return chartBottom - ((value - minValue) / valueRange) * chartHeight
            }

            fun indexToX(index: Int): Float {
                return chartLeft + (index.toFloat() / (data.size - 1)) * chartWidth
            }

            val labelStyle = TextStyle(
                color = Color.Black,
                fontSize = 8.sp,
                lineHeight = 8.sp,
                letterSpacing = 0.em,
                fontFamily = dmSansFamily,
                fontWeight = FontWeight.Normal
            )

            // Draw dashed grid lines & Y labels
            val yLabels = listOf(25f, 50f, 75f)

            yLabels.forEach { value ->
                val y = valueToY(value)

                drawLine(
                    color = dashedLineColor,
                    start = Offset(chartLeft, y),
                    end = Offset(chartRight, y),
                    strokeWidth = 1.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(
                        floatArrayOf(3.dp.toPx(), 3.dp.toPx()), // EXACT Figma dash ratio
                        0f
                    )
                )

                drawText(
                    textMeasurer = textMeasurer,
                    text = value.toInt().toString(),
                    style = labelStyle,
                    topLeft = Offset(0f, y - 4.dp.toPx())
                )
            }

            // Build paths
            val points = data.mapIndexed { index, (_, value) ->
                Offset(indexToX(index), valueToY(value))
            }

            val linePath = Path().apply {
                moveTo(points[0].x, points[0].y)
                for (i in 0 until points.size - 1) {
                    val p0 = points[i]
                    val p1 = points[i + 1]
                    val controlX1 = p0.x + (p1.x - p0.x) / 2f
                    val controlY1 = p0.y
                    val controlX2 = p0.x + (p1.x - p0.x) / 2f
                    val controlY2 = p1.y
                    cubicTo(controlX1, controlY1, controlX2, controlY2, p1.x, p1.y)
                }
            }

            val fillPath = Path().apply {
                addPath(linePath)
                lineTo(points.last().x, chartBottom)
                lineTo(points.first().x, chartBottom)
                close()
            }

            clipRect(left = chartLeft, top = chartTop, right = chartRight, bottom = chartBottom) {
                // Gradient Fill Area
                drawPath(
                    path = fillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(fillColorTop, fillColorBottom),
                        startY = chartTop,
                        endY = chartBottom
                    )
                )

                drawPath(
                    path = linePath,
                    color = lineColor,
                    style = Stroke(
                        width = 1.15.dp.toPx(),
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }

            // Draw data point circles
            points.forEach { point ->
                drawIntoCanvas { canvas ->
                    val shadowPaint = Paint().apply {
                        isAntiAlias = true
                        color = android.graphics.Color.WHITE
                        setShadowLayer(
                            24f,
                            0f,
                            0f,
                            android.graphics.Color.argb(130, 224, 112, 112)
                        )
                    }
                    canvas.nativeCanvas.drawCircle(
                        point.x,
                        point.y,
                        5.dp.toPx(),
                        shadowPaint
                    )
                }

                drawCircle(
                    color = lineColor,
                    radius = 2.5.dp.toPx(),
                    center = point
                )
            }

            // X labels
            data.forEachIndexed { index, (label, _) ->
                val x = indexToX(index)
                drawText(
                    textMeasurer = textMeasurer,
                    text = label,
                    style = labelStyle,
                    topLeft = Offset(x - (textMeasurer.measure(label, labelStyle).size.width / 2f), chartBottom + 8.dp.toPx())
                )
            }
        }
    }
}

@Composable
private fun ToggleButton(
    label: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(50.dp)
            .height(32.dp)
            .background(
                color = if (isActive) Color.Black else Color(0xFFF7F6F6),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontFamily = dmSansFamily,
            fontWeight = if (isActive) FontWeight.Medium else FontWeight.Normal,
            fontSize = 10.sp,
            lineHeight = 10.sp,
            letterSpacing = 0.em,
            color = if (isActive) Color.White else Color(0xFF696770)
        )
    }
}