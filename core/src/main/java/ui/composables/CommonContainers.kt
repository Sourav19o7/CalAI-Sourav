package ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import second.brain.main_resources.R

@Composable
fun AppBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colorResource(R.color.blue_1),
                        colorResource(R.color.blue_6),
                    )
                )
            )
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(400.dp)
                .offset(
                    x = (-150).dp,
                    y = (-150).dp
                )
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            colorResource(R.color.blue_3).copy(alpha = 0.2f),
                            colorResource(R.color.transparent)
                        )
                    )
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(400.dp)
                .offset(
                    x = 150.dp,
                    y = 150.dp
                )
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            colorResource(R.color.blue_3).copy(alpha = 0.2f),
                            colorResource(R.color.transparent)
                        )
                    )
                )
        )
    }
}
