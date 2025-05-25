package ui.text

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import second.brain.main_resources.R

val LexendNormal = FontFamily(
    Font(resId = R.font.regular)
)
// Define Typography
val AppTypography = Typography(
    bodySmall = TextStyle( // Body (S) Bold
        fontFamily = LexendNormal,
        fontSize = 13.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
)