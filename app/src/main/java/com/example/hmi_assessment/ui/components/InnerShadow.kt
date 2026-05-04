package com.example.hmi_assessment.ui.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.innerShadow(
    shape: Shape,
    color: Color = Color.Black,
    blur: Dp = 4.dp,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 2.dp
): Modifier = this.drawWithContent {
    drawContent()

    val blurPx = blur.toPx()
    val offsetXAmount = offsetX.toPx()
    val offsetYAmount = offsetY.toPx()

    if (blurPx == 0f) return@drawWithContent

    val outline = shape.createOutline(size, layoutDirection, this)

    val shapePath = Path().apply {
        when (outline) {
            is Outline.Rounded -> addRoundRect(outline.roundRect)
            is Outline.Rectangle -> addRect(outline.rect)
            else -> addPath(Path().apply { addRect(outline.bounds) })
        }
    }

    drawIntoCanvas { canvas ->
        canvas.save()
        canvas.clipPath(shapePath)

        val paint = Paint().apply {
            asFrameworkPaint().apply {
                isAntiAlias = true
                // FIX: Transparent base color prevents the solid frame from creating a 1px artifact outline at the boundaries
                this.color = android.graphics.Color.TRANSPARENT
                setShadowLayer(
                    blurPx,
                    offsetXAmount,
                    offsetYAmount,
                    color.toArgb()
                )
            }
        }

        val outerPath = Path().apply {
            addRect(Rect(-size.width, -size.height, size.width * 2, size.height * 2))
        }

        val hollowFramePath = Path().apply {
            op(outerPath, shapePath, PathOperation.Difference)
        }

        canvas.drawPath(hollowFramePath, paint)
        canvas.restore()
    }
}