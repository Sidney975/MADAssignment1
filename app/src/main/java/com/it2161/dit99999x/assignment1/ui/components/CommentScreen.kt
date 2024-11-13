package com.it2161.dit99999x.assignment1.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentScreen(navController: NavController, movieTitle: String, commentJson: String) {
    val context = LocalContext.current
    val application = context.applicationContext as MovieRaterApplication
    val movieList = remember { application.getMovies(context) }
    val movie = movieList.find { it.title == movieTitle }
    val comment = Gson().fromJson(commentJson, Comments::class.java) // Convert JSON back to Comments object
    if (comment != null) {
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
                // User initials
                Text(getUserInitials(comment.user), style = MaterialTheme.typography.headlineMedium)

                Spacer(modifier = Modifier.height(8.dp))

                // Duration
                val duration = calculateDuration(comment.date, comment.time)

                Text(duration, style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(16.dp))

                // Comment details
                Text("Comment: ${comment.comment}", style = MaterialTheme.typography.bodyMedium)


            }
        }
    }
}

// Function to get user initials
fun getUserInitials(username: String): String {
    val words = username.split(Regex("(?<=[a-z])(?=[A-Z])|_|\\s+")) // Split by camel case, underscore, or whitespace
    return when (words.size) {
        0 -> ""
        1 -> words[0].substring(0, 1).uppercase() // If only one word, take the first letter
        else -> words.joinToString(".") { it.substring(0, 1).uppercase() } // Otherwise, take first letters of each word
    }
}
