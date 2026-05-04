package com.example.hmi_assessment.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

@Composable
fun CustomGridIcon(modifier: Modifier = Modifier) {
    val purpleColor = Color(0xFFB4A8DA)
    val grayColor = Color(0xFFDCD9E7)

    Canvas(modifier = modifier) {
        val canvasSize = size.minDimension
        val circleRadius = canvasSize * 0.22f

        // Top Left (Purple)
        drawCircle(
            color = purpleColor,
            radius = circleRadius,
            center = Offset(canvasSize * 0.25f, canvasSize * 0.25f)
        )
        // Top Right (Gray)
        drawCircle(
            color = grayColor,
            radius = circleRadius,
            center = Offset(canvasSize * 0.75f, canvasSize * 0.25f)
        )
        // Bottom Left (Gray)
        drawCircle(
            color = grayColor,
            radius = circleRadius,
            center = Offset(canvasSize * 0.25f, canvasSize * 0.75f)
        )
        // Bottom Right (Purple)
        drawCircle(
            color = purpleColor,
            radius = circleRadius,
            center = Offset(canvasSize * 0.75f, canvasSize * 0.75f)
        )
    }
}