package second.brain.feature_onboarding.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import constants.ScreenConstants
import second.brain.feature_onboarding.presentation.event.OnboardingEvent
import second.brain.feature_onboarding.presentation.state.OnboardingState
import second.brain.main_resources.R
import ui.composables.TextOnly

@Composable
fun UserDetailsScreen(
    state: OnboardingState,
    onEvent : (OnboardingEvent) -> Unit,
    onBack : () -> Unit,
    navigateTo: (ScreenConstants) -> Unit
) {

    val annotateString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = colorResource(R.color.gray_5),
                fontSize = 15.sp,
                fontFamily = FontFamily(
                    Font(R.font.light)
                )
            )
        ) {
            append("Using ")
        }

        withStyle(
            style = SpanStyle(
                color = Color.White,
                fontSize = 15.sp,
                fontFamily = FontFamily(
                    Font(R.font.bold)
                )
            )
        ) {
            append("${state.email} ")
        }

        withStyle(
            style = SpanStyle(
                color = colorResource(R.color.gray_5),
                fontSize = 15.sp,
                fontFamily = FontFamily(
                    Font(R.font.light)
                )
            )
        ) {
            append("to login")
        }


    }

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 30.dp, vertical = 20.dp)
    ) {
        Box(
            modifier = Modifier.border(
                width = 2.dp,
                color = colorResource(R.color.gray_4),
                shape = RoundedCornerShape(10.dp)
            ).clickable {
                onBack()
            }
        ) {

            Image(
                painter = painterResource(id = R.drawable.left_arrow),
                contentDescription = "Back",
                modifier = Modifier
                    .size(35.dp)
                    .padding(8.dp)
            )

        }

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            modifier = Modifier,
            lineHeight = 50.sp,
            text = "Sign up",
            fontSize = 40.sp,
            fontFamily = FontFamily(
                Font(R.font.semibold)
            ),
            color = Color.White
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = annotateString
        )


        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "YOUR NAME",
            fontFamily = FontFamily(
                Font(R.font.regular)
            ),
            fontSize = 13.sp,
            color = colorResource(R.color.gray_1)
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = state.name,
            onValueChange = { name ->
                onEvent(OnboardingEvent.NameChanged(name))
            },
            placeholder = {
                Text(
                    text = "Name",
                    fontFamily = FontFamily(
                        Font(R.font.medium)
                    ),
                    fontSize = 16.sp,
                    color = colorResource(R.color.gray_5)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = colorResource(R.color.green_1),
                unfocusedIndicatorColor = colorResource(R.color.gray_1),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent

            ),
            trailingIcon = {
                Icon(
                    tint = colorResource(R.color.gray_5),
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cross",
                    modifier = Modifier
                        .size(22.dp)
                        .background(color = colorResource(R.color.gray_2), shape = CircleShape)
                        .padding(4.dp)
                )
            }
        )
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "PHONE NUMBER",
            fontFamily = FontFamily(
                Font(R.font.regular)
            ),
            fontSize = 13.sp,
            color = colorResource(R.color.gray_1)
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = state.phoneNumber,
            onValueChange = {
                onEvent(OnboardingEvent.PhoneNumberChanged(it))
            },
            placeholder = {
                Text(
                    text = "Phone",
                    fontFamily = FontFamily(
                        Font(R.font.medium)
                    ),
                    fontSize = 16.sp,
                    color = colorResource(R.color.gray_5)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = colorResource(R.color.green_1),
                unfocusedIndicatorColor = colorResource(R.color.gray_1),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent

            ),
            trailingIcon = {
                Icon(
                    tint = colorResource(R.color.gray_5),
                    imageVector = Icons.Default.Close,
                    contentDescription = "cross",
                    modifier = Modifier
                        .size(22.dp)
                        .background(color = colorResource(R.color.gray_2), shape = CircleShape)
                        .padding(4.dp)
                )
            }
        )

        Spacer(modifier = Modifier.height(50.dp))

        TextOnly(
            containerModifier = Modifier,
            text = "Sign Up",
            backgroundColor = R.color.blue_2
        ) {
            onEvent(OnboardingEvent.RegisterUser {
                if (it) {
                    navigateTo(ScreenConstants.HomeScreen)
                }
            })
        }

    }

}