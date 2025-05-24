package second.brain.feature_onboarding.presentation.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import constants.ScreenConstants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import second.brain.feature_onboarding.presentation.state.OnboardingState
import second.brain.main_resources.R
import ui.composables.DrawableOnly
import ui.composables.IconWithText

@Composable
fun OnboardingScreen(
    state: OnboardingState,
    signInWithGoogle: () -> Unit,
    navigateTo: (ScreenConstants) -> Unit
) {

    val context = LocalContext.current
    LaunchedEffect(state.signInErrorMessage) {
        state.signInErrorMessage?.let {
            Toast.makeText(
                context,
                it,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize(), verticalArrangement = Arrangement.Bottom
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                PagerScreens()
            }

            Spacer(modifier = Modifier.height(30.dp))

            IconWithText(
                containerModifier = Modifier.padding(horizontal = 30.dp),
                text = "Continue with Email",
                icon = Icons.Default.Email,
                backgroundColor = R.color.blue_2
            ) {
                navigateTo(ScreenConstants.SignUp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                DrawableOnly(
                    containerModifier = Modifier.weight(1f),
                    drawable = R.drawable.google,
                    backgroundColor = R.color.transparent,
                    borderColor = R.color.gray_1,
                    tint = colorResource(R.color.pink_1)
                ) {
                    signInWithGoogle()
                }

                DrawableOnly(
                    containerModifier = Modifier.weight(1f),
                    drawable = R.drawable.facebook,
                    backgroundColor = R.color.transparent,
                    borderColor = R.color.gray_1,
                    tint = colorResource(R.color.blue_3)
                ) {

                }

            }

            Spacer(modifier = Modifier.height(30.dp))

            TermsAndConditions()

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun PagerScreens() {

    val imageList = listOf(
        R.drawable.onboarding_desk_1,
        R.drawable.onboarding_desk_2,
        R.drawable.onboarding_desk_3
    )

    val textList = listOf(
        listOf(
            "Task,",
            "Calendar,",
            "Chat"
        ),
        listOf(
            "Work",
            "Anywhere",
            "Easily"
        ),
        listOf(
            "Manage",
            "Everything",
            "on Phone"
        )
    )
    val pagerState = rememberPagerState(
        pageCount = {
            imageList.size
        }
    )
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(pagerState.currentPage) {
        coroutineScope.launch {
            delay(3000)
            pagerState.scrollToPage(
                (pagerState.currentPage + 1) % pagerState.pageCount
            )
        }
    }

    Image(
        modifier = Modifier
            .fillMaxWidth(),
        contentScale = ContentScale.Crop,
        painter = painterResource(imageList[pagerState.currentPage]),
        contentDescription = null
    )

    Column {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            userScrollEnabled = false
        ) { page ->

            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                    TextAnimation(
                        textList = textList[page]
                    )
                }

            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row(
            Modifier
                .wrapContentHeight()
                .padding(start = 30.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(imageList.size) { iteration ->
                val color by animateColorAsState(
                    targetValue =
                    if ((pagerState.currentPage == iteration)) {
                        colorResource(R.color.blue_2)
                    } else {
                        colorResource(
                            R.color.gray_4
                        )
                    },
                    label = ""
                )
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp)
                )
            }
        }
    }
}


@Composable
fun TextAnimation(
    textList: List<String>
) {

    val animationDuration = 1000
    var isVisible by remember { mutableStateOf(false) }


    // This launches the animation for visibility when the screen appears
    LaunchedEffect(Unit) {
        // Delay the animation for a nice effect
        isVisible = true
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        textList.forEachIndexed { index, text ->
            AnimatedVisibility(
                visible = isVisible, enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = animationDuration,
                        delayMillis = index * (animationDuration / 3)
                    )
                ) + slideIn(
                    animationSpec = tween(
                        durationMillis = animationDuration,
                        delayMillis = index * (animationDuration / 3)
                    ),
                    initialOffset = {
                        IntOffset(x = -100, y = 0)
                    },
                )
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp),
                    lineHeight = 50.sp,
                    text = text,
                    fontSize = 40.sp,
                    fontFamily = FontFamily(
                        Font(R.font.semibold)
                    ),
                    color = Color.White
                )
            }
        }
    }
}


@Composable
fun TermsAndConditions() {

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 60.dp),
        textAlign = TextAlign.Center,
        text = "By continuing, you agree to our Terms of Service and Privacy Policy",
        color = colorResource(R.color.gray_1),
        fontFamily = FontFamily(
            Font(R.font.light)
        ),
        lineHeight = 20.sp,
        fontSize = 12.sp
    )

}
