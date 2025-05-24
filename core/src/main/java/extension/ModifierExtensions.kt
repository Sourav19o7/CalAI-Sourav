package extension

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

fun Modifier.leftBorder(color: Color, width: Dp) = this.drawWithContent {
    drawContent()
    drawLine(
        color = color,
        start = Offset(0f, 0f),
        end = Offset(0f, size.height),
        strokeWidth = width.toPx()
    )
}

fun Modifier.bottomBorder(color: Color, width: Dp) = this.drawWithContent {
    drawContent()
    drawLine(
        color = color,
        start = Offset(0f, size.height),
        end = Offset(size.width, size.height),
        strokeWidth = width.toPx()
    )
}

inline fun Modifier.conditional(
    condition: Boolean,
    pass: Modifier.() -> Modifier,
    fail: Modifier.() -> Modifier = { this },
): Modifier = if (condition) {
    then(pass(Modifier))
} else {
    then(fail(Modifier))
}