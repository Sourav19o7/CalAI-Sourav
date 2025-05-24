package ui.composables

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CustomPolygonShape(
    modifier: Modifier = Modifier,
    sides: Int = 5,  // Default is a pentagon
    color: List<Color> = listOf(Color.Blue, Color.Red)
) {

    val finalSides = if (sides < 3) 3 else sides

    Canvas(modifier = modifier) {
        val path = Path()
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radius = size.minDimension / 2.2f
        val angle = 360f / finalSides

        for (i in 0 until finalSides) {
            val theta = Math.toRadians((angle * i - 90).toDouble())
            val x = (centerX + radius * cos(theta)).toFloat()
            val y = (centerY + radius * sin(theta)).toFloat()
            if (i == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }
        path.close()

        drawPath(path = path, brush = Brush.verticalGradient(
            colors = color
        ), style = Fill)
    }
}
