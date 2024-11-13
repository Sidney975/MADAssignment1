package com.it2161.dit99999x.assignment1.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.it2161.dit99999x.assignment1.LocalLoggedInUser
import com.it2161.dit99999x.assignment1.MovieRaterApplication
import com.it2161.dit99999x.assignment1.PopCornMovie
import com.it2161.dit99999x.assignment1.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val application = context.applicationContext as MovieRaterApplication
    val loggedInUser = application.loggedInUser // Access loggedInUser here

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Profile", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(PopCornMovie.Landing.name) }) { // Navigate back
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(PopCornMovie.editProfile.name) }) {
                        Icon(Icons.Filled.Edit, contentDescription = "Edit")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val avatarResource = when (loggedInUser?.avatar) {
                "avatar_1" -> R.drawable.avatar_1
                "avatar_2" -> R.drawable.avatar_2
                "avatar_3" -> R.drawable.avatar_3
                else -> R.drawable.ic_account_circle // Default placeholder
            }
            val gender = when (loggedInUser?.gender) {
                "M" -> "Male"
                "F" -> "Female"
                "NB" -> "Non-Binary"
                else -> "Nill"
            }
            Image(
                painter = painterResource(id = avatarResource),
                contentDescription = "Avatar",
                modifier = Modifier.size(128.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Display user details from loggedInUser
            loggedInUser?.let { user ->
                Text("Name: ${user.userName}")
                Text("Email: ${user.email}")
                Text("Gender: ${user.gender}")
                Text("Phone: ${user.mobile}")
            }
        }
    }
}