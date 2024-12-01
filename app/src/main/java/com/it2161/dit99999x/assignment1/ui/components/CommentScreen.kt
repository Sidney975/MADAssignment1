// 232074A
// Lee Siqi, Sidney


package com.it2161.dit99999x.assignment1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.it2161.dit99999x.assignment1.MovieRaterApplication
import com.it2161.dit99999x.assignment1.data.Comments
import com.it2161.dit99999x.assignment1.data.calculateDuration
import com.it2161.dit99999x.assignment1.data.getUserInitials
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentScreen(navController: NavController, movieTitle: String, commentJson: String) {
    val context = LocalContext.current
    val application = context.applicationContext as MovieRaterApplication
    val movieList = remember { application.data }
    val movie = movieList.find { it.title == movieTitle }
    val comment = Gson().fromJson(commentJson, Comments::class.java) // Convert JSON back to Comments object

    if (comment != null) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            movieTitle,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },
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
                verticalArrangement = Arrangement.Center // Center the content vertically
            ) {
                // Top section for User Initial and Comment Details
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    // User Initial with Circle
                    Box(
                        modifier = Modifier
                            .size(75.dp)
                            .clip(shape = CircleShape) // Clip the box into a circle
                            .background(MaterialTheme.colorScheme.primary), // Background color for the circle
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = getUserInitials(comment.user),
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onPrimary // Text color for contrast
                        )
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    // Comment Details
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = comment.user, // Full name of the commenter
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Posted on: ${comment.date}", // Date posted
                            style = MaterialTheme.typography.bodySmall
                        )

                        // Calculate duration
                        val duration = calculateDuration(comment.date, comment.time)
                        Text(
                            text = "$duration", // How long ago it was posted
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Comment content
                Text(
                    text = comment.comment,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center // Align the comment text to the center
                )
            }
        }
    }
}

