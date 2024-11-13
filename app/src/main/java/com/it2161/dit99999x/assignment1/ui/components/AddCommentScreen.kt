package com.it2161.dit99999x.assignment1.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.it2161.dit99999x.assignment1.LocalLoggedInUser
import com.it2161.dit99999x.assignment1.R
import com.it2161.dit99999x.assignment1.data.Comments
import java.text.SimpleDateFormat
import java.util.*
import com.it2161.dit99999x.assignment1.LocalMovieList
import com.it2161.dit99999x.assignment1.MovieRaterApplication
import com.it2161.dit99999x.assignment1.data.MovieItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCommentScreen(
    navController: NavController,
    movieTitle: String,
    onCommentAdded: (MovieItem) -> Unit
) {
    val context = LocalContext.current
    val application = context.applicationContext as MovieRaterApplication
    val loggedInUser = application.loggedInUser
    var commentText by remember { mutableStateOf("") }
    val movieList = LocalMovieList.current.toMutableList()
    val movie = movieList.find { it.title == movieTitle }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(movieTitle, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Movie image bitmap
            val imageBitmap = remember {
                movie?.image?.let { application.getImgVector(it).asImageBitmap() }
            }

            imageBitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = movieTitle,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(16.dp)
                )
            }

            // Comment input field
            OutlinedTextField(
                value = commentText,
                onValueChange = { commentText = it },
                label = { Text("Add comment") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Submit button
            Button(onClick = {
                println(commentText)
                println(loggedInUser)
                println(movie)
                if (commentText.isNotBlank() && loggedInUser != null && movie != null) {
                    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                    val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                    val newComment = Comments(loggedInUser.userName, commentText, currentDate, currentTime)

                    // Append the new comment to the movie's comment list
                    val updatedComments = movie.comment + newComment
                    val updatedMovie = movie.copy(comment = updatedComments)

                    // Call the onCommentAdded callback to update movieList in MovieViewerApp
                    onCommentAdded(updatedMovie)

                }
            }) {
                Text("Submit")
            }
        }
    }
}
