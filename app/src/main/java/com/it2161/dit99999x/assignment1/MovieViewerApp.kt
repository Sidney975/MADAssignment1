package com.it2161.dit99999x.assignment1

import androidx.annotation.StringRes
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.it2161.dit99999x.assignment1.data.MovieItem
import com.it2161.dit99999x.assignment1.data.UserProfile
import com.it2161.dit99999x.assignment1.ui.components.*


enum class PopCornMovie(@StringRes val title: Int) {
    Login(title = R.string.login),
    Register(title = R.string.register),
    Landing(title = R.string.landing),
    viewProfile(title = R.string.viewProfile),
    editProfile(title = R.string.editProfile),
    movieDetail(title = R.string.movieDetail),
    commentScreen(title = R.string.commentScreen),
    addComment(title = R.string.addComment),
}


@Composable
fun MovieViewerApp() {
    val context = LocalContext.current
    val application = context.applicationContext as MovieRaterApplication
    val navController = rememberNavController()
    var movieList by remember { mutableStateOf(application.getMovies(context)) }

    // Callback function to update movieList with new comments
    fun onCommentAdded(updatedMovie: MovieItem) {
        movieList = movieList.map { movie ->
            if (movie.title == updatedMovie.title) updatedMovie else movie
        }
    }

    CompositionLocalProvider(
        LocalMovieList provides movieList,
        LocalLoggedInUser provides application.loggedInUser
    ) {
        NavHost(navController = navController, startDestination = PopCornMovie.Login.name) {
            composable(route = PopCornMovie.Login.name) {
                LoginScreen(
                    onLoginSuccess = {
                        if (application.userProfile != null && application.loginUser(application.userProfile!!)) {
                                navController.navigate(PopCornMovie.Landing.name)
                        }
                    },
                    onNavigateToRegister = { navController.navigate(PopCornMovie.Register.name) }
                )
            }
            composable(route = PopCornMovie.Register.name) {
                RegisterUserScreen(
                    onRegisterSuccess = { newUserProfile ->
                        application.userProfile = newUserProfile
                        navController.navigate(PopCornMovie.Login.name)
                    },
                    onCancel = { navController.navigate(PopCornMovie.Login.name) }
                )
            }
            composable(route = PopCornMovie.Landing.name) {
                LandingScreen(navController)
            }
            composable(route = PopCornMovie.viewProfile.name) {
                ProfileScreen(navController)
            }
            composable(PopCornMovie.editProfile.name) {
                EditProfileScreen(
                    navController,
                    onUpdateSuccess = { updatedUser ->
                        application.userProfile = updatedUser
                        application.loggedInUser = updatedUser
                        navController.navigate(PopCornMovie.viewProfile.name)
                    },
                    onCancel = { navController.navigate(route = PopCornMovie.viewProfile.name) }
                )
            }
            composable(
                route = "${PopCornMovie.movieDetail.name}/{movieTitle}",
                arguments = listOf(navArgument("movieTitle") { type = NavType.StringType })
            ) { backStackEntry ->
                val movieTitle = backStackEntry.arguments?.getString("movieTitle") ?: ""
                MovieDetailScreen(navController, movieTitle)
            }
            composable(
                route = "${PopCornMovie.commentScreen.name}/{movieTitle}/{commentJson}",
                arguments = listOf(
                    navArgument("movieTitle") { type = NavType.StringType },
                    navArgument("commentJson") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val movieTitle = backStackEntry.arguments?.getString("movieTitle") ?: ""
                val commentJson = backStackEntry.arguments?.getString("commentJson") ?: ""
                CommentScreen(navController, movieTitle, commentJson)
            }
            composable(
                route = "${PopCornMovie.addComment.name}/{movieTitle}",
                arguments = listOf(navArgument("movieTitle") { type = NavType.StringType })
            ) { backStackEntry ->
                val movieTitle = backStackEntry.arguments?.getString("movieTitle") ?: ""
                AddCommentScreen(
                    navController = navController,
                    movieTitle = movieTitle,
                    onCommentAdded = { updatedMovie ->
                        onCommentAdded(updatedMovie)
                        navController.popBackStack("${PopCornMovie.movieDetail.name}/$movieTitle", inclusive = false)
                    }
                )
            }
        }
    }
}

// Define CompositionLocal for the logged-in user
val LocalLoggedInUser = compositionLocalOf<UserProfile?> { null }
val LocalMovieList = compositionLocalOf<List<MovieItem>> { emptyList() }

