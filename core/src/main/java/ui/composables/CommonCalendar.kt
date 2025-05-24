package ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import second.brain.main_resources.R
import ui.text.AppTypography
import java.time.LocalDate
import java.time.YearMonth


data class CalendarDateSpannerItem(
    val date : LocalDate,
    val isCurrentMonth : Boolean
)

@Composable
fun CalenderDateSpanner(
    modifier: Modifier = Modifier,
    screenWidth: Dp = 400.dp,
    selectedDates: Set<LocalDate> = setOf(
        LocalDate.now(),
        LocalDate.now().minusDays(1),
        LocalDate.now().minusDays(2),
        LocalDate.now().minusDays(3),
        LocalDate.now().minusDays(4),
        LocalDate.now().minusDays(5),
        LocalDate.now().minusDays(6),
        LocalDate.now().minusDays(9),
    ),
    onDaySelected: (LocalDate) -> Unit = {}
) {
    var displayMonth by remember {
        mutableStateOf(YearMonth.now())
    }
    val calendarPadding = 20.dp
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(R.color.gray_2), RoundedCornerShape(20.dp))
                .padding(calendarPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = colorResource(R.color.gray_4),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable {
                            displayMonth = displayMonth.minusMonths(1)
                        }
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.left_arrow),
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(30.dp)
                            .padding(8.dp)
                    )

                }
                Text(
                    color = colorResource(R.color.green_8),
                    text = displayMonth.month.name + " " + displayMonth.year.toString(),
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    style = AppTypography.titleSmall,
                    textAlign = TextAlign.Center
                )
                Box(
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = colorResource(R.color.gray_4),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable {
                            displayMonth = displayMonth.plusMonths(1)
                        }
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.left_arrow),
                        contentDescription = "Back",
                        modifier = Modifier
                            .rotate(180f)
                            .size(30.dp)
                            .padding(8.dp)
                    )

                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            WeekHeader(
                screenWidth = screenWidth
            )

            Spacer(modifier = Modifier.height(8.dp))

            MonthGrid(
                selectedDates = selectedDates,
                displayMonth = displayMonth,
                onDateSelected = onDaySelected,
            )
        }
    }
}

@Composable
private fun MonthGrid(
    selectedDates: Set<LocalDate> = setOf(),
    displayMonth: YearMonth = YearMonth.now(),
    onDateSelected: (LocalDate) -> Unit = {}
) {

    val daysInMonth = displayMonth.lengthOfMonth()
    // Adjust the firstDayOfWeek to make Monday the first day of the week
    val firstDayOfWeek = (displayMonth.atDay(1).dayOfWeek.value - 1).let {
        if (it == 0) 7 else it // If Sunday (value 7), shift to the end of the week
    }

    val dayOffset = (1..firstDayOfWeek).toList()
    val daysOfMonth = (1..daysInMonth).map {
        CalendarDateSpannerItem(
            date = LocalDate.of(displayMonth.year, displayMonth.month, it), isCurrentMonth = true
        )
    }

    // Days from the previous month
    val previousMonth = displayMonth.minusMonths(1)
    val daysInPreviousMonth = previousMonth.lengthOfMonth()
    val previousMonthDays = (daysInPreviousMonth - firstDayOfWeek + 1..daysInPreviousMonth).map {
        CalendarDateSpannerItem(
            date = LocalDate.of(previousMonth.year, previousMonth.month, it), isCurrentMonth = false
        )
    }

    // Days from the next month
    val nextMonth = displayMonth.plusMonths(1)

    //To calculate the edge case where number of rows can be 5 or 6.
    //Hence total dates will be 35 or 42
    val remainingDays = if (dayOffset.size + daysOfMonth.size > 35) {
        42 - (dayOffset.size + daysOfMonth.size)
    } else {
        35 - (dayOffset.size + daysOfMonth.size)
    }
    val nextMonthDays = (1..remainingDays).map {
        CalendarDateSpannerItem(
            date = LocalDate.of(nextMonth.year, nextMonth.month, it), isCurrentMonth = false
        )
    }

    val allDays = previousMonthDays + daysOfMonth + nextMonthDays

    Column {
        for (chunk in allDays.chunked(7)) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(vertical = 5.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color.Transparent, Color.Transparent)
                        ),
                        shape = RoundedCornerShape(50.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (day in chunk) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    ) {
                        DayItem(
                            day = day.date,
                            currentMonth = day.isCurrentMonth,
                            selectedDates = selectedDates,
                            onDateSelected = {
                                onDateSelected(it)
                            },
                            firstInRow = (day == chunk[0]),
                            lastInRow = (day == chunk[chunk.size - 1])
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun WeekHeader(
    screenWidth: Dp,
) {
    Row(
        modifier = Modifier
            .width(screenWidth)
            .padding(bottom = 8.dp),
    ) {
        val weekDays = listOf("M", "T", "W", "T", "F", "S", "S")
        for (day in weekDays) {
            Text(
                textAlign = TextAlign.Center,
                text = day,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                style = AppTypography.bodyLarge,
                color = colorResource(R.color.gray_4)
            )
        }
    }
}

@Composable
private fun DayItem(
    day: LocalDate,
    currentMonth: Boolean,
    selectedDates: Set<LocalDate>,
    onDateSelected: (LocalDate) -> Unit,
    firstInRow: Boolean,
    lastInRow: Boolean,
    navigateToHistory:(LocalDate) -> Unit = {}
) {
    val isSelected = if (currentMonth) {
        selectedDates.contains(day)
    } else {
        false
    }

    val isCurrent = day == LocalDate.now()

    val textColor = if (currentMonth) {
            Color.White

    } else {
            Color.White.copy(0.5f)

    }
    val isPrevSelected = if (currentMonth) {
        selectedDates.contains(day.minusDays(1)) && (day.month == day.minusDays(1).month)
    } else {
        false
    }
    val isNextSelected = if (currentMonth) {
        selectedDates.contains(day.plusDays(1)) && (day.month == day.plusDays(1).month)
    } else {
        false
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(
                if (lastInRow) {
                    if (isPrevSelected && isNextSelected) {
                        RoundedCornerShape(
                            bottomStart = 0.dp,
                            topStart = 0.dp,
                            topEnd = 50.dp,
                            bottomEnd = 50.dp
                        )
                    } else if (isPrevSelected) {
                        RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp)
                    } else if (isNextSelected) {
                        RoundedCornerShape(
                            topStart = 50.dp,
                            bottomStart = 50.dp,
                            topEnd = 50.dp,
                            bottomEnd = 50.dp
                        )
                    } else {
                        RoundedCornerShape(100.dp)
                    }
                } else if (firstInRow) {
                    if (isPrevSelected && isNextSelected) {
                        RoundedCornerShape(
                            bottomEnd = 0.dp,
                            topEnd = 0.dp,
                            topStart = 50.dp,
                            bottomStart = 50.dp
                        )
                    } else if (isPrevSelected) {
                        RoundedCornerShape(
                            topEnd = 50.dp,
                            bottomEnd = 50.dp,
                            topStart = 50.dp,
                            bottomStart = 50.dp
                        )
                    } else if (isNextSelected) {
                        RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp)
                    } else {
                        RoundedCornerShape(100.dp)
                    }
                } else {
                    if (isPrevSelected && isNextSelected) {
                        RoundedCornerShape(
                            bottomEnd = 0.dp,
                            topEnd = 0.dp,
                            topStart = 0.dp,
                            bottomStart = 0.dp
                        )
                    } else if (isPrevSelected) {
                        RoundedCornerShape(
                            topEnd = 50.dp,
                            bottomEnd = 50.dp,
                            topStart = 0.dp,
                            bottomStart = 0.dp
                        )
                    } else if (isNextSelected) {
                        RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp)
                    } else {
                        RoundedCornerShape(100.dp)
                    }
                }
            )
            .background(
                color = if (isSelected ) {
                    colorResource(R.color.blue_2)
                } else {
                    Color.Transparent
                }
            )
            .border(
                width = 2.dp,
                color = if (isCurrent && !isSelected) {
                    colorResource(R.color.blue_2)
                } else {
                    Color.Transparent
                },
                shape = RoundedCornerShape(100.dp)
            )
            .clickable {

                onDateSelected(day)


                navigateToHistory(day)

            }, contentAlignment = Center
    ) {


            Text(
                style = AppTypography.bodyLarge,
                text = day.dayOfMonth.toString(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 5.dp),
                textAlign = TextAlign.Center,
                color = textColor
            )

    }
}