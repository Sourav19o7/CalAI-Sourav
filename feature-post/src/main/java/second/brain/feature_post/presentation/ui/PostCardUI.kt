package second.brain.feature_post.presentation.ui

import androidx.compose.material.icons.filled.Add
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import extension.LoadImage
import second.brain.feature_post.domain.model.Post
import second.brain.main_resources.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PostCard(
    post: Post,
    currentUserId: String,
    onLikeClick: (String) -> Unit,
    onUnlikeClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showMenu by remember { mutableStateOf(false) }
    val isLiked = post.likedBy.contains(currentUserId)
    val isOwnPost = post.userId == currentUserId

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.transparent)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Header with user info and menu
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                ) {
                    // Profile Image
                    if (post.userProfileImage.isNotEmpty()) {
                        post.userProfileImage.LoadImage(
                            size = 60.dp,
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(colorResource(R.color.gray_4)),
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(colorResource(R.color.gray_4)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = post.userName.take(1).uppercase(),
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            maxLines = 1,
                            modifier = Modifier.basicMarquee(),
                            text = post.userName,
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.medium))
                        )
                        Text(
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            text = post.content,
                            fontSize = 16.sp,
                        )
                    }
                }

                Spacer(modifier = Modifier.width(20.dp))
                Box {
                    Text(
                        text = formatTimestamp(post.timestamp),
                        fontSize = 14.sp
                    )
                }

            }

//            Spacer(modifier = Modifier.height(12.dp))
//
//            Text(
//                text = post.content,
//                color = Color.White,
//                fontSize = 14.sp,
//                fontFamily = FontFamily(Font(R.font.regular)),
//                lineHeight = 20.sp
//            )

//            if (!post.imageUrl.isNullOrEmpty()) {
//                Spacer(modifier = Modifier.height(12.dp))
//                post.imageUrl.LoadImage(
//                    size = 200.dp,
//                    modifier = Modifier.fillMaxWidth()
//                    .height(200.dp)
//                    .clip(RoundedCornerShape(8.dp))
//                )
//
//            }
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            // Actions (like, comment)
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                // Like button
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier.clickable {
//                        if (isLiked) {
//                            onUnlikeClick(post.id)
//                        } else {
//                            onLikeClick(post.id)
//                        }
//                    }
//                ) {
//                    Icon(
//                        imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
//                        contentDescription = if (isLiked) "Unlike" else "Like",
//                        tint = if (isLiked) Color.Red else colorResource(R.color.gray_5),
//                        modifier = Modifier.size(20.dp)
//                    )
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text(
//                        text = "${post.likesCount}",
//                        color = colorResource(R.color.gray_5),
//                        fontSize = 12.sp,
//                        fontFamily = FontFamily(Font(R.font.regular))
//                    )
//                }
//
//                Spacer(modifier = Modifier.width(16.dp))
//
//                // Comments (placeholder for future implementation)
//                Row(
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Add,
//                        contentDescription = "Comments",
//                        tint = colorResource(R.color.gray_5),
//                        modifier = Modifier.size(20.dp)
//                    )
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text(
//                        text = "${post.commentsCount}",
//                        color = colorResource(R.color.gray_5),
//                        fontSize = 12.sp,
//                        fontFamily = FontFamily(Font(R.font.regular))
//                    )
//                }
//            }
        }
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp))
}