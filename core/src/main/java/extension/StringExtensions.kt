package extension

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import coil.compose.SubcomposeAsyncImage

@Composable
fun String.LoadImage(
    modifier: Modifier = Modifier,
    size: Dp,
) {
    SubcomposeAsyncImage(
        model = this,
        contentDescription = "thumbnail_holder",
        modifier = modifier
            .size(size),
        contentScale = ContentScale.Crop,
        loading = {
            Box(
                modifier = Modifier.size(size)
            ) {

            }
        }
    )
}