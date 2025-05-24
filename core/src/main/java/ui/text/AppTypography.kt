package ui.text

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import second.brain.main_resources.R


// Define the Lexend Font Family
val LexendSemibold = FontFamily(
    Font(resId = R.font.semibold)
)
val LexendBold = FontFamily(
    Font(resId = R.font.bold)
)
val LexendMedium = FontFamily(
    Font(resId = R.font.medium)
)
val LexendNormal = FontFamily(
    Font(resId = R.font.regular)
)
// Define Typography
val AppTypography = Typography(
    displayLarge = TextStyle( // H0
        fontFamily = LexendSemibold,
        fontSize = 60.sp,
        lineHeight = 64.sp,
        letterSpacing = 0.sp
    ),
    displayMedium = TextStyle( // H1
        fontFamily = LexendSemibold,
        fontSize = 48.sp,
        lineHeight = 56.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle( // H2
        fontFamily = LexendSemibold,
        fontSize = 40.sp,
        lineHeight = 48.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle( // H3
        fontFamily = LexendSemibold,

        fontSize = 36.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle( // H4
        fontFamily = LexendSemibold,
        
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle( // H5
        fontFamily = LexendSemibold,
        
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle( // H6
        fontFamily = LexendSemibold,
        
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    titleSmall = TextStyle( // Title
        fontFamily = LexendSemibold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    labelLarge = TextStyle( // Button (L)
        fontFamily = LexendBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    labelMedium = TextStyle( // Button (S)
        fontFamily = LexendBold,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle( // Tab
        fontFamily = LexendSemibold,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle( // Body (L)
        fontFamily = LexendMedium,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle( // Body (S)
        fontFamily = LexendMedium,
        fontSize = 13.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    bodySmall = TextStyle( // Body (S) Bold
        fontFamily = LexendBold,
        fontSize = 13.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
)