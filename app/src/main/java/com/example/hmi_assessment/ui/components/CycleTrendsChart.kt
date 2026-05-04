package com.example.hmi_assessment.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.em
import com.example.hmi_assessment.R
import com.example.hmi_assessment.ui.theme.dmSansFamily

data class CycleData(
    val month: String,
    val totalDays: Int,
    val periodOffsetDays: Int = 0,
    val ovulationDay: Int
)

@Composable
fun CycleTrendsChart(modifier: Modifier = Modifier) {
    val cycles = listOf(
        CycleData("Jan", 28, 0, ovulationDay = 20),
        CycleData("Feb", 30, 3, ovulationDay = 22),
        CycleData("Mar", 28, 3, ovulationDay = 20),
        CycleData("Apr", 32, 3, ovulationDay = 24),
        CycleData("May", 28, 3, ovulationDay = 20),
        CycleData("Jun", 28, 0, ovulationDay = 20),
    )

    val barColor       = Color(0xFFB4A8DA)
    val dashedLineColor = Color(0xFFE0DDDD)
    val mensColor      = Color(0xFFE99597)
    val ovulationColor = Color(0xFF6E8C82)
    val arrowColor     = Color(0xFFB4A8DA)

    val barWidthDp: Dp = 13.dp
    val maxBarHeightDp: Dp = 160.dp
    val maxDays = cycles.maxOf { it.totalDays }
    val bottomLabelSpaceDp = 32.dp

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = null,
            tint = arrowColor,
            modifier = Modifier
                .size(20.dp)
                .border(1.dp, arrowColor, CircleShape)
                .padding(2.dp)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                val lineW = size.width
                val bottomY = size.height - bottomLabelSpaceDp.toPx()
                val height28 = maxBarHeightDp.toPx() * (28f / maxDays.toFloat())

                val topY = bottomY - height28
                val midY = bottomY - (height28 / 2f)

                val dashEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f)

                drawLine(dashedLineColor, Offset(0f, topY), Offset(lineW, topY), strokeWidth = 2.5f, pathEffect = dashEffect)
                drawLine(dashedLineColor, Offset(0f, midY), Offset(lineW, midY), strokeWidth = 2.5f, pathEffect = dashEffect)
                drawLine(dashedLineColor, Offset(0f, bottomY), Offset(lineW, bottomY), strokeWidth = 2.5f, pathEffect = dashEffect)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Bottom
            ) {
                cycles.forEach { cycle ->
                    CycleBar(
                        cycle = cycle,
                        maxDays = maxDays,
                        barWidth = barWidthDp,
                        maxBarHeight = maxBarHeightDp,
                        barColor = barColor,
                        mensColor = mensColor,
                        ovulationColor = ovulationColor,
                        bottomLabelSpace = bottomLabelSpaceDp
                    )
                }
            }
        }

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = arrowColor,
            modifier = Modifier
                .size(20.dp)
                .border(1.dp, arrowColor, CircleShape)
                .padding(2.dp)
        )
    }
}

@Composable
private fun CycleBar(
    cycle: CycleData,
    maxDays: Int,
    barWidth: Dp,
    maxBarHeight: Dp,
    barColor: Color,
    mensColor: Color,
    ovulationColor: Color,
    bottomLabelSpace: Dp
) {
    val dpPerDay = maxBarHeight / maxDays.toFloat()
    val barHeight = dpPerDay * cycle.totalDays

    val mensBottomOffsetDp = dpPerDay * cycle.periodOffsetDays
    val ovulationCenterDp = dpPerDay * cycle.ovulationDay

    val fixedZoneHeight = 34.dp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        // PRECISE TOP TEXT (Days)
        Text(
            text = cycle.totalDays.toString(),
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 10.sp,
            fontFamily = dmSansFamily,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        Box(
            modifier = Modifier
                .width(barWidth)
                .height(barHeight)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height
                val cornerR = w / 2f
                val zH = fixedZoneHeight.toPx()

                drawRoundRect(
                    color = barColor,
                    size = Size(w, h),
                    cornerRadius = CornerRadius(cornerR, cornerR)
                )

                // GREEN OVULATION ZONE
                val greenTop = h - ovulationCenterDp.toPx() - (zH / 2f)
                drawRoundRect(
                    color = ovulationColor,
                    topLeft = Offset(0f, greenTop),
                    size = Size(w, zH),
                    cornerRadius = CornerRadius(cornerR, cornerR)
                )

                // PINK MENSTRUATION ZONE
                val pinkTop = h - zH - mensBottomOffsetDp.toPx()
                drawRoundRect(
                    color = mensColor,
                    topLeft = Offset(0f, pinkTop),
                    size = Size(w, zH),
                    cornerRadius = CornerRadius(cornerR, cornerR)
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.gear),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = ovulationCenterDp - 5.dp)
                    .size(8.dp)
            )

            Icon(
                painter = painterResource(id = R.drawable.drop),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = mensBottomOffsetDp + 6.dp)
                    .size(8.dp)
            )
        }

        // Months
        Box(
            modifier = Modifier.height(bottomLabelSpace),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                text = cycle.month,
                fontSize = 8.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 8.sp,
                fontFamily = dmSansFamily,
                color = Color.Black,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}