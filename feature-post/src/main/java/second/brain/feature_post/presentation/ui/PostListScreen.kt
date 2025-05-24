package second.brain.feature_post.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import second.brain.feature_post.presentation.event.PostsEvent
import second.brain.feature_post.presentation.viewmodel.PostsViewModel
import second.brain.main_resources.R

@Composable
fun PostsListScreen(
    viewModel: PostsViewModel,
    onCreatePostClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    // Handle errors
    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when {
            state.isLoading && state.posts.isEmpty() -> {
                // Initial loading state
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = colorResource(R.color.blue_2),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Loading posts...",
                            color = colorResource(R.color.gray_5),
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.regular))
                        )
                    }
                }
            }

            state.posts.isEmpty() && !state.isLoading -> {
                // Empty state
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Text(
                            text = "No posts yet",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontFamily = FontFamily(Font(R.font.semibold))
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Be the first to share something!",
                            color = colorResource(R.color.gray_5),
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.regular)),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            else -> {
                // Posts list
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    items(
                        items = state.posts,
                        key = { post -> post.id }
                    ) { post ->
                        PostCard(
                            post = post,
                            currentUserId = state.currentUserId,
                            onLikeClick = { postId ->
                                viewModel.onEvent(PostsEvent.LikePost(postId))
                            },
                            onUnlikeClick = { postId ->
                                viewModel.onEvent(PostsEvent.UnlikePost(postId))
                            },
                            onDeleteClick = { postId ->
                                viewModel.onEvent(PostsEvent.DeletePost(postId))
                            }
                        )
                    }

                    // Bottom spacing for FAB
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }


        // Floating Action Button for creating posts
        FloatingActionButton(
            onClick = onCreatePostClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = colorResource(R.color.blue_2),
            contentColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Create Post",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}