package com.it2161.dit99999x.assignment1.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.it2161.dit99999x.assignment1.LocalLoggedInUser
import com.it2161.dit99999x.assignment1.MovieRaterApplication
import com.it2161.dit99999x.assignment1.R
import com.it2161.dit99999x.assignment1.data.UserProfile
import android.util.Log // Import for logging

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    onUpdateSuccess: (UserProfile) -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current
    val application = context.applicationContext as MovieRaterApplication
    val loggedInUser = application.loggedInUser
    var showAvatarMenu by remember { mutableStateOf(false) }

    var userName by remember { mutableStateOf(loggedInUser?.userName ?: "") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var email by remember { mutableStateOf(loggedInUser?.email ?: "") }
    var gender by remember { mutableStateOf(loggedInUser?.gender ?: "") }
    var mobile by remember { mutableStateOf(loggedInUser?.mobile ?: "") }
    var receiveUpdates by remember { mutableStateOf(loggedInUser?.updates ?: false) }
    var yearOfBirth by remember { mutableStateOf(loggedInUser?.yob ?: "") }
    var selectedAvatar by remember { mutableStateOf(loggedInUser?.avatar ?: "") }

    var userNameError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var genderError by remember { mutableStateOf(false) }
    var mobileError by remember { mutableStateOf(false) }
    var yearOfBirthError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Edit Profile", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        try {
                            // Validation logic
                            userNameError = userName.isBlank()
                            passwordError = password.isBlank()
                            confirmPasswordError = confirmPassword.isBlank() || confirmPassword != password
                            emailError = email.isBlank()
                            genderError = gender.isBlank()
                            mobileError = mobile.isBlank()
                            yearOfBirthError = yearOfBirth.isBlank()

                            if (!(userNameError || passwordError || confirmPasswordError || emailError || genderError || mobileError || yearOfBirthError)) {
                                val updatedUser = loggedInUser?.copy(
                                    userName = userName,
                                    password = password,
                                    email = email,
                                    gender = gender,
                                    mobile = mobile,
                                    updates = receiveUpdates,
                                    yob = yearOfBirth,
                                    avatar = selectedAvatar
                                )
                                application.userProfile = updatedUser
                                application.loggedInUser = updatedUser
                                Log.d("EditProfileScreen", "Updated User: $updatedUser") // Log the updated user profile
                                navController.popBackStack() // Navigate back
                            } else {
                                Log.d("EditProfileScreen", "Validation failed with errors.")
                            }
                        } catch (e: Exception) {
                            Log.e("EditProfileScreen", "Error updating profile: ${e.localizedMessage}", e)
                        }
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Save")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()), // Make the column scrollable
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Profile picture with click functionality
            Box(modifier = Modifier.clickable { showAvatarMenu = true }) {
                val avatarResource = when (selectedAvatar) {
                    "avatar_1" -> R.drawable.avatar_1
                    "avatar_2" -> R.drawable.avatar_2
                    "avatar_3" -> R.drawable.avatar_3
                    else -> R.drawable.ic_account_circle // Default placeholder
                }
                Image(
                    painter = painterResource(id = avatarResource),
                    contentDescription = "Avatar",
                    modifier = Modifier.size(128.dp)
                )
            }

            // Avatar selection menu (DropdownMenu) - positioned below the profile picture
            DropdownMenu(
                expanded = showAvatarMenu,
                onDismissRequest = { showAvatarMenu = false },
                modifier = Modifier
                    .width(128.dp) // Match the width of the profile picture
                    .wrapContentSize(Alignment.TopCenter) // Position below the profile picture
            ) {
                DropdownMenuItem(
                    onClick = { selectedAvatar = "avatar_1"; showAvatarMenu = false },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(painter = painterResource(id = R.drawable.avatar_1), contentDescription = "Avatar 1", modifier = Modifier.size(32.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Avatar 1")
                        }
                    }
                )
                DropdownMenuItem(
                    onClick = { selectedAvatar = "avatar_2"; showAvatarMenu = false },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(painter = painterResource(id = R.drawable.avatar_2), contentDescription = "Avatar 2", modifier = Modifier.size(32.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Avatar 2")
                        }
                    }
                )
                DropdownMenuItem(
                    onClick = { selectedAvatar = "avatar_3"; showAvatarMenu = false },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(painter = painterResource(id = R.drawable.avatar_3), contentDescription = "Avatar 3", modifier = Modifier.size(32.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Avatar 3")
                        }
                    }
                )
                DropdownMenuItem(
                    onClick = { selectedAvatar = ""; showAvatarMenu = false },
                    text = { Text("Remove Avatar") }
                )
            }

            // Input fields for user details (similar to RegisterUserScreen)
            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text("User Name") },
                modifier = Modifier.fillMaxWidth(),
                isError = userNameError,
                supportingText = { if (userNameError) Text("User name is required") else null }
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = passwordError,
                supportingText = { if (passwordError) Text("Password is required") else null }
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = confirmPasswordError,
                supportingText = { if (confirmPasswordError) Text("Passwords do not match") else null }
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = emailError,
                supportingText = { if (emailError) Text("Email is required") else null }
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Gender selection (Radio buttons)
            Row {
                RadioButton(selected = gender == "Male", onClick = { gender = "Male" })
                Text("Male")
                RadioButton(selected = gender == "Female", onClick = { gender = "Female" })
                Text("Female")
            }
            Row {
                RadioButton(selected = gender == "Non-Binary", onClick = { gender = "Non-Binary" })
                Text("Non-Binary")
                RadioButton(selected = gender == "Prefer not to say", onClick = { gender = "Prefer not to say" })
                Text("Prefer not to say")
            }
            if (genderError) {
                Text("Gender is required", color = MaterialTheme.colorScheme.error)
            }
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = mobile,
                onValueChange = { mobile = it },
                label = { Text("Mobile Number") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                isError = mobileError,
                supportingText = { if (mobileError) Text("Mobile number is required") else null }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Checkbox(checked = receiveUpdates, onCheckedChange = { receiveUpdates = it })
                Text("Receive Updates")
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Year of birth (Dropdown)
            var expanded by remember { mutableStateOf(false) }
            val years = (1920..2023).toList() // Generate years from 1920 to 2023
            OutlinedTextField(
                value = yearOfBirth,
                onValueChange = { yearOfBirth = it },
                label = { Text("Year of Birth") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(painterResource(id = R.drawable.ic_off_visibility_icon), contentDescription = "Dropdown")
                    }
                },
                isError = yearOfBirthError,
                supportingText = { if (yearOfBirthError) Text("Year of birth is required") else null }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                years.forEach { year ->
                    DropdownMenuItem(onClick = {
                        yearOfBirth = year.toString()
                        expanded = false
                    }, text = { Text(year.toString()) })
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            // Other UI components like Avatar Image, TextFields, DropdownMenu, etc.

            Button(onClick = {
                try {
                    // Field validation
                    userNameError = userName.isBlank()
                    passwordError = password.isBlank()
                    confirmPasswordError = confirmPassword.isBlank() || confirmPassword != password
                    emailError = email.isBlank()
                    genderError = gender.isBlank()
                    mobileError = mobile.isBlank()
                    yearOfBirthError = yearOfBirth.isBlank()

                    if (!(userNameError || passwordError || confirmPasswordError || emailError || genderError || mobileError || yearOfBirthError)) {
                        val userProfile = UserProfile(userName, password, email, gender, mobile, receiveUpdates, yearOfBirth, selectedAvatar)
                        Log.d("EditProfileScreen", "Updated UserProfile: $userProfile")
                        onUpdateSuccess(userProfile)
                    } else {
                        Log.d("EditProfileScreen", "Validation failed. Errors in input fields.")
                    }
                } catch (e: Exception) {
                    Log.e("EditProfileScreen", "Exception during submission: ${e.localizedMessage}", e)
                }
            }) {
                Text("Submit")
            }
        }
    }
}

// Helper composable for displaying an avatar image
@Composable
fun AvatarImage(
    imageResource: Int,
    avatarId: String,
    selectedAvatar: String,
    onClick: (String) -> Unit
) {
    val isSelected = avatarId == selectedAvatar
    IconButton(onClick = { onClick(avatarId) }) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(64.dp)
                .then(
                    if (isSelected) Modifier.border(
                        2.dp,
                        MaterialTheme.colorScheme.primary
                    ) else Modifier
                )
        )
    }
}