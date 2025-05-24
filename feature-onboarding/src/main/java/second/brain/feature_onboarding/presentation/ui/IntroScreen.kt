package second.brain.feature_onboarding.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import constants.ScreenConstants
import second.brain.main_resources.R

@Composable
fun IntroScreen(
    userLoggedIn: Boolean,
    navigateTo: (ScreenConstants) -> Unit
) {
    Column {
        Image(
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .weight(1f)
                .statusBarsPadding()
                .fillMaxSize(),
            contentDescription = null,
            painter = painterResource(id = R.drawable.intro_people)
        )

        Spacer(modifier = Modifier.height(60.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(start = 30.dp)) {
                Text(
                    lineHeight = 14.sp,
                    text = "Task Management👋",
                    fontFamily = FontFamily(
                        Font(R.font.bold)
                    ),
                    fontSize = 14.sp,
                    color = colorResource(R.color.purple_2)
                )

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    modifier = Modifier,
                    lineHeight = 50.sp,
                    text = "Let's create\na space\n" +
                            "for your\nworkflows.",
                    fontSize = 40.sp,
                    fontFamily = FontFamily(
                        Font(R.font.semibold)
                    ),
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(30.dp))

                Box(
                    modifier = Modifier
                        .shadow(
                            elevation = 7.dp,
                            shape = RoundedCornerShape(30.dp),
                            spotColor = Color.Black,
                            ambientColor = Color.Black
                        )
                        .background(
                            color = colorResource(R.color.blue_2),
                            shape = RoundedCornerShape(25.dp)
                        )
                        .padding(vertical = 12.dp, horizontal = 22.dp)
                        .clickable {
                            if (userLoggedIn) {
                                navigateTo(ScreenConstants.HomeScreen)
                            } else {
                                navigateTo(ScreenConstants.OnboardingScreen)
                            }
                        },
                ) {
                    Text(
                        text = "Get Started",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(
                            Font(R.font.medium)
                        ),
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(70.dp))

            }

            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(150.dp)
                    .offset(x = 90.dp, y = (-50).dp)
                    .rotate(45f)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                colorResource(R.color.green_6), colorResource(R.color.green_3)
                            )
                        ),
                        shape = RoundedCornerShape(30.dp)
                    )
                    .clickable {
                        navigateTo(ScreenConstants.OnboardingScreen)
                    }
                    .clip(
                        RoundedCornerShape(30.dp)
                    )

            ) {
                Icon(
                    tint = Color.Black,
                    modifier = Modifier
                        .size(25.dp)
                        .offset(y = 100.dp, x = 20.dp)
                        .rotate(-225f),
                    contentDescription = null,
                    painter = painterResource(id = R.drawable.left_arrow)
                )
            }
        }

    }
}