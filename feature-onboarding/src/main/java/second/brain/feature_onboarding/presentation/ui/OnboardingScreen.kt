package second.brain.feature_onboarding.presentation.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .background(
                    color = colorResource(R.color.blue_1),
                    shape = RoundedCornerShape(30.dp)
                )
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .clickable(null, null, onClick = { signInWithGoogle() })
        ) {
            Text(
                text = "Sign In With Google",
                color = Color.White,
                fontFamily = FontFamily(
                    Font(R.font.medium)
                ),
                fontSize = 18.sp
            )
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
