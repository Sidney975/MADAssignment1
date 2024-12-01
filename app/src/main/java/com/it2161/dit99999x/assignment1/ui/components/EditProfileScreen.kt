// 232074A
// Lee Siqi, Sidney


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
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight

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

    // State variables for user inputs
    var userName by remember { mutableStateOf(loggedInUser?.userName ?: "") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var email by remember { mutableStateOf(loggedInUser?.email ?: "") }
    var gender by remember { mutableStateOf(loggedInUser?.gender ?: "") }
    var mobile by remember { mutableStateOf(loggedInUser?.mobile ?: "") }
    var receiveUpdates by remember { mutableStateOf(loggedInUser?.updates ?: false) }
    var yearOfBirth by remember { mutableStateOf(loggedInUser?.yob ?: "") }
    var selectedAvatar by remember { mutableStateOf(loggedInUser?.avatar ?: "") }
    var showAvatarMenu by remember { mutableStateOf(false) }
    var yearDropdownExpanded by remember { mutableStateOf(false) }

    // Error state variables for validation
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
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = {
                        onCancel()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        // Perform validation and save action
                        userNameError = userName.isBlank()
                        passwordError = password.isBlank()
                        confirmPasswordError = confirmPassword.isBlank() || confirmPassword != password
                        emailError = email.isBlank() || !email.contains("@")
                        genderError = gender.isBlank()
                        mobileError = mobile.isBlank() || !mobile.all { it.isDigit() }
                        yearOfBirthError = yearOfBirth.isBlank()

                        if (!(userNameError || passwordError || confirmPasswordError || emailError || genderError || mobileError || yearOfBirthError)) {
                            val updatedUser = UserProfile(
                                userName = userName,
                                password = password,
                                email = email,
                                gender = gender,
                                mobile = mobile,
                                updates = receiveUpdates,
                                yob = yearOfBirth,
                                avatar = selectedAvatar
                            )
                            onUpdateSuccess(updatedUser)
                        }
                    }) {
                        Icon(Icons.Filled.Check, contentDescription = "Save")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .background(Color.White)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .clickable { showAvatarMenu = true }
                    .wrapContentSize(Alignment.Center)
            ) {
                val avatarResource = when (selectedAvatar) {
                    "avatar_1" -> R.drawable.avatar_1
                    "avatar_2" -> R.drawable.avatar_2
                    "avatar_3" -> R.drawable.avatar_3
                    else -> R.drawable.ic_account_circle // Default placeholder
                }
                Image(
                    painter = painterResource(id = avatarResource),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(128.dp)
                        .padding(8.dp)
                )

                // Avatar Selection Dropdown Menu
                DropdownMenu(
                    expanded = showAvatarMenu,
                    onDismissRequest = { showAvatarMenu = false },
                    modifier = Modifier.wrapContentSize(Alignment.Center)
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
            }


            Spacer(modifier = Modifier.height(16.dp))

            // User Input Fields
            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text("User Name") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                isError = userNameError,
                supportingText = { if (userNameError) Text("User name is required") else null }
            )
            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = passwordError,
                supportingText = { if (passwordError) Text("Password is required") else null }
            )
            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = confirmPasswordError,
                supportingText = { if (confirmPasswordError) Text("Passwords do not match") else null }
            )
            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = emailError,
                supportingText = { if (emailError) Text("Valid email is required") else null }
            )
            Spacer(modifier = Modifier.height(6.dp))

            // Gender Selection (Radio Buttons)
            Row(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .horizontalScroll(rememberScrollState()), // Add horizontal scroll
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = gender == "Male", onClick = { gender = "Male" })
                Text("Male")
                RadioButton(selected = gender == "Female", onClick = { gender = "Female" })
                Text("Female")
                RadioButton(selected = gender == "Non-Binary", onClick = { gender = "Non-Binary" })
                Text("Non-Binary")
                RadioButton(selected = gender == "Prefer not to say", onClick = { gender = "Prefer not to say" })
                Text("Prefer not to say")
            }
            if (genderError) {
                Text("Gender is required", color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(horizontal = 16.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))


//            Row(
//                modifier = Modifier
//                    .padding(bottom = 16.dp)
//                    .horizontalScroll(rememberScrollState()), // Add horizontal scroll
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                RadioButton(selected = gender == "Male", onClick = { gender = "Male" })
//                Text("Male")
//                RadioButton(selected = gender == "Female", onClick = { gender = "Female" })
//                Text("Female")
//                RadioButton(selected = gender == "Non-Binary", onClick = { gender = "Non-Binary" })
//                Text("Non-Binary")
//                RadioButton(selected = gender == "Prefer not to say", onClick = { gender = "Prefer not to say" })
//                Text("Prefer not to say")
//            }
//            if (genderError) {
//                Text("Gender is required", color = MaterialTheme.colorScheme.error)
//            }
//            Spacer(modifier = Modifier.height(6.dp))


            OutlinedTextField(
                value = mobile,
                onValueChange = { mobile = it },
                label = { Text("Mobile Number") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                isError = mobileError,
                supportingText = { if (mobileError) Text("Valid mobile number is required") else null }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = receiveUpdates,
                    onCheckedChange = { receiveUpdates = it }
                )
                Text("Receive Updates")
            }

            Spacer(modifier = Modifier.height(16.dp))




            // Year of Birth Dropdown
            OutlinedTextField(
                value = yearOfBirth,
                onValueChange = { yearOfBirth = it },
                label = { Text("Year of Birth") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { yearDropdownExpanded = true }) {
                        Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Dropdown")
                    }
                },
                isError = yearOfBirthError,
                supportingText = { if (yearOfBirthError) Text("Year of birth is required") else null }
            )
            DropdownMenu(
                expanded = yearDropdownExpanded,
                onDismissRequest = { yearDropdownExpanded = false }
            ) {
                (1920..2023).forEach { year ->
                    DropdownMenuItem(
                        onClick = {
                            yearOfBirth = year.toString()
                            yearDropdownExpanded = false
                        },
                        text = { Text(year.toString()) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
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