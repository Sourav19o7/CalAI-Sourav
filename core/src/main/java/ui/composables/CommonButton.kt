package ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import second.brain.main_resources.R


object ButtonConstants {
    val BUTTON_HEIGHT = 55.dp
    val BUTTON_FONT_FAMILY = FontFamily(Font(R.font.regular))
    val BUTTON_FONT_SIZE = 15.sp
    val ICON_SIZE = 25.dp
}

@Composable
fun IconWithText(
    containerModifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    backgroundColor: Int,
    onClick: () -> Unit,
) {
    Row(
        modifier = containerModifier
            .fillMaxWidth()
            .height(ButtonConstants.BUTTON_HEIGHT)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(30.dp),
                spotColor = Color.Black
            )
            .background(
                color = colorResource(backgroundColor),
                shape = RoundedCornerShape(30.dp)
            )
            .clip(RoundedCornerShape(30.dp))
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Icon(
            tint = Color.White,
            modifier = Modifier.size(ButtonConstants.ICON_SIZE),
            imageVector = icon,
            contentDescription = null,
        )


        Spacer(modifier = Modifier.width(2.dp))

        Text(
            text = text,
            color = Color.White,
            fontFamily = ButtonConstants.BUTTON_FONT_FAMILY,
            fontSize = ButtonConstants.BUTTON_FONT_SIZE,
            lineHeight = ButtonConstants.BUTTON_FONT_SIZE,
            modifier = Modifier.padding(10.dp)
        )


    }
}

@Composable
fun DrawableWithText(
    containerModifier: Modifier = Modifier,
    text: String,
    drawable: Int,
    backgroundColor: Int,
    onClick: () -> Unit,
) {
    Row(
        modifier = containerModifier
            .fillMaxWidth()
            .height(ButtonConstants.BUTTON_HEIGHT)
            .background(
                color = colorResource(backgroundColor),
                shape = RoundedCornerShape(30.dp)
            )
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Image(
            modifier = Modifier.size(ButtonConstants.ICON_SIZE),
            painter = painterResource(drawable),
            contentDescription = null,
        )

        if (text.isNotEmpty()) {
            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = text,
                color = Color.White,
                fontFamily = ButtonConstants.BUTTON_FONT_FAMILY,
                fontSize = ButtonConstants.BUTTON_FONT_SIZE,
                lineHeight = ButtonConstants.BUTTON_FONT_SIZE,
                modifier = Modifier.padding(10.dp)
            )
        }

    }
}

@Composable
fun IconOnly(
    containerModifier: Modifier = Modifier,
    icon: ImageVector,
    backgroundColor: Int,
    onClick: () -> Unit,
) {
    Row(
        modifier = containerModifier
            .fillMaxWidth()
            .height(ButtonConstants.BUTTON_HEIGHT)
            .background(
                color = colorResource(backgroundColor),
                shape = RoundedCornerShape(30.dp)
            )
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Icon(
            tint = Color.White,
            modifier = Modifier.size(ButtonConstants.ICON_SIZE),
            imageVector = icon,
            contentDescription = null,
        )

    }
}

@Composable
fun DrawableOnly(
    containerModifier: Modifier = Modifier,
    drawable: Int,
    backgroundColor: Int,
    borderColor: Int,
    tint: Color? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = containerModifier
            .fillMaxWidth()
            .height(ButtonConstants.BUTTON_HEIGHT)
            .clip(
                RoundedCornerShape(30.dp)
            )
            .background(
                color = colorResource(backgroundColor),
                shape = RoundedCornerShape(30.dp)
            )
            .clickable { onClick() }
            .border(
                width = 1.dp,
                color = colorResource(borderColor),
                shape = RoundedCornerShape(30.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        if (tint != null) {
            Icon(
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(ButtonConstants.ICON_SIZE),
                painter = painterResource(drawable),
            )
        } else {
            Image(
                modifier = Modifier.size(ButtonConstants.ICON_SIZE),
                painter = painterResource(drawable),
                contentDescription = null,
            )
        }

    }
}

@Composable
fun TextOnly(
    containerModifier: Modifier = Modifier,
    text: String,
    backgroundColor: Int,
    onClick: () -> Unit,
) {

    Row(
        modifier = containerModifier
            .fillMaxWidth()
            .height(ButtonConstants.BUTTON_HEIGHT)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(30.dp),
                spotColor = Color.Black
            )
            .background(
                color = colorResource(backgroundColor),
                shape = RoundedCornerShape(30.dp)
            )
            .clip(RoundedCornerShape(30.dp))
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            text = text,
            color = Color.White,
            fontFamily = ButtonConstants.BUTTON_FONT_FAMILY,
            fontSize = ButtonConstants.BUTTON_FONT_SIZE,
            lineHeight = ButtonConstants.BUTTON_FONT_SIZE,
            modifier = Modifier.padding(10.dp)
        )

    }
}