package com.example.hmi_assessment.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hmi_assessment.R // Replace with your actual R import if needed
import com.example.hmi_assessment.ui.theme.dmSansFamily

// import com.example.hmi_assessment.ui.theme.dmSansFamily

@Composable
fun CustomFloatingFooter(
    modifier: Modifier = Modifier,
    currentRoute: String = "Insights",
    onNavigate: (String) -> Unit = {},
    onFabClick: () -> Unit = {}
) {
    // Exact colors from your specs
    val activeColor = Color.Black
    val inactiveColor = Color.Black.copy(alpha = 0.4f) // #000000 at 40%
    val ambientShadow = Color(0xFF000000).copy(alpha = 0.06f)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0f),
                        Color.White.copy(alpha = 0.9f),
                        Color.White
                    )
                )
            )
            .navigationBarsPadding()
            .padding(start = 20.dp, end = 20.dp, top = 0.dp, bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Nav pill
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(72.dp)
                    // Drop Shadow
                    .shadow(
                        elevation = 16.dp,
                        shape = RoundedCornerShape(percent = 50),
                        spotColor = ambientShadow,
                        ambientColor = ambientShadow
                    )
                    .background(Color.White, RoundedCornerShape(percent = 50))
                    // Inner Shadow (Dark/Depth)
                    .innerShadow(
                        shape = RoundedCornerShape(percent = 50),
                        color = Color.Black.copy(alpha = 0.08f),
                        blur = 8.dp,
                        offsetX = 1.dp,
                        offsetY = 3.dp
                    )
                    // Inner Highlight (Light Rim)
                    .innerShadow(
                        shape = RoundedCornerShape(percent = 50),
                        color = Color.White.copy(alpha = 0.9f),
                        blur = 4.dp,
                        offsetX = (-2).dp,
                        offsetY = (-2).dp
                    ),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NavItem(R.drawable.home, "Home", currentRoute == "Home", activeColor, inactiveColor) { onNavigate("Home") }
                    NavItem(R.drawable.clock, "Track", currentRoute == "Track", activeColor, inactiveColor) { onNavigate("Track") }
                    NavItem(R.drawable.stats, "Insights", currentRoute == "Insights", activeColor, inactiveColor) { onNavigate("Insights") }
                }
            }

            // FAB
            Box(
                modifier = Modifier
                    .size(72.dp)
                    // Drop Shadow
                    .shadow(
                        elevation = 16.dp,
                        shape = CircleShape,
                        spotColor = ambientShadow,
                        ambientColor = ambientShadow
                    )
                    .background(Color.White, CircleShape)
                    // Inner Shadow (Dark/Depth)
                    .innerShadow(
                        shape = CircleShape,
                        color = Color.Black.copy(alpha = 0.08f),
                        blur = 8.dp,
                        offsetX = 1.dp,
                        offsetY = 3.dp
                    )
                    // Inner Highlight (Light Rim)
                    .innerShadow(
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.9f),
                        blur = 4.dp,
                        offsetX = (-2).dp,
                        offsetY = (-2).dp
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onFabClick
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = "Add",
                    tint = inactiveColor, // Assuming the plus uses the same inactive grey as the designs
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
private fun NavItem(
    @DrawableRes iconRes: Int,
    label: String,
    isSelected: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    onClick: () -> Unit
) {
    val color = if (isSelected) activeColor else inactiveColor
    Column(
        modifier = Modifier
            .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null, onClick = onClick)
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            tint = color, // This will apply the active/inactive color to your SVG
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp)) // Tiny spacing between icon and text
        Text(
            text = label,
            fontFamily = dmSansFamily,
            fontSize = 10.sp, // Figma 10px translates directly to 10.sp in Compose
            color = color,
            fontWeight = FontWeight.Normal // 400 Weight is 'Normal'
        )
    }
}