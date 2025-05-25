package second.brain.feature_post.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import second.brain.feature_post.presentation.event.PostsEvent
import second.brain.feature_post.presentation.viewmodel.PostsViewModel
import second.brain.main_resources.R
import ui.text.AppTypography

@Composable
fun PostsListScreen(
    viewModel: PostsViewModel,
    onCreatePostClick: (String) -> Unit,
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

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    item{
                        CreatePostItem{
                            onCreatePostClick(it)
                        }
                    }
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
    }
}

@Composable
fun CreatePostItem(
    onCreatePostClick: (String) -> Unit
) {

    val focusManager = LocalFocusManager.current
    var newPost by remember {
        mutableStateOf("")
    }
    Column{
        TextField(

            modifier = Modifier
                .fillMaxWidth().padding(start = 10.dp),
            value = newPost,
            onValueChange = {
                newPost = it
            },
            placeholder = {
                Text(
                    text = "Write something here",
                    style = AppTypography.bodySmall.copy(
                        fontSize = 20.sp
                    ),
                    color = colorResource(R.color.gray_1)
                )
            },
            textStyle = AppTypography.bodySmall.copy(
                textAlign = TextAlign.Start,
                fontSize = 20.sp
            ),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onCreatePostClick(newPost)
                    focusManager.clearFocus()
                    newPost = ""
                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(modifier = Modifier.padding(start = 20.dp)){
            Box(
                modifier = Modifier
                    .background(
                        color = colorResource(R.color.blue_1),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(vertical = 10.dp, horizontal = 20.dp)
                    .clickable(null, null, onClick = {
                        onCreatePostClick(newPost)
                        newPost = ""
                        focusManager.clearFocus()
                    })
            ) {
                Text(
                    text = "Add to the wall",
                    color = Color.White,
                    fontFamily = FontFamily(
                        Font(R.font.light)
                    ),
                    fontSize = 18.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        HorizontalDivider(
            thickness = 2.dp,
            color = Color.Black.copy(0.07f)
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}
