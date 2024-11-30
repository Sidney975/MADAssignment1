package com.it2161.dit99999x.assignment1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.it2161.dit99999x.assignment1.R
import com.it2161.dit99999x.assignment1.data.UserProfile
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterUserScreen(
    onRegisterSuccess: (UserProfile) -> Unit,
    onCancel: () -> Unit
) {
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var receiveUpdates by remember { mutableStateOf(false) }
    var yearOfBirth by remember { mutableStateOf("") }

    var userNameError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var genderError by remember { mutableStateOf(false) }
    var mobileError by remember { mutableStateOf(false) }
    var yearOfBirthError by remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "Register",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("User Name") },
            modifier = Modifier.widthIn(max = screenWidth * 0.8f),
            isError = userNameError,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary, // Customize border color
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f) // Customize border color
            ),
            supportingText = { if (userNameError) Text("User name is required") else null }
        )
        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.widthIn(max = screenWidth * 0.8f),
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
            modifier = Modifier.widthIn(max = screenWidth * 0.8f),
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
            modifier = Modifier.widthIn(max = screenWidth * 0.8f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = emailError,
            supportingText = { if (emailError) Text("Valid email is required") else null }
        )
        Spacer(modifier = Modifier.height(6.dp))

        // Gender selection (Radio buttons)
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
            Text("Gender is required", color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = mobile,
            onValueChange = { mobile = it },
            label = { Text("Mobile Number") },
            modifier = Modifier.widthIn(max = screenWidth * 0.8f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            isError = mobileError,
            supportingText = { if (mobileError) Text("Valid mobile number is required") else null }
        )
        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.padding(bottom = 16.dp), // Add padding
            verticalAlignment = Alignment.CenterVertically // Align items vertically
        ) {
            Checkbox(checked = receiveUpdates, onCheckedChange = { receiveUpdates = it })
            Text("Receive Updates")
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Year of birth (Dropdown)
        var expanded by remember { mutableStateOf(false) }
        val years = (1920..2023).toList() // Generate years from 1920 to 2023
        OutlinedTextField(
            value = yearOfBirth,
            onValueChange = { yearOfBirth = it },
            label = { Text("Year of Birth") },
            modifier = Modifier.widthIn(max = screenWidth * 0.8f),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "Dropdown")
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

        Button(onClick = {
            userNameError = userName.isBlank()
            passwordError = password.isBlank()
            confirmPasswordError = confirmPassword.isBlank() || confirmPassword != password
            emailError = email.isBlank() || !email.contains("@")
            genderError = gender.isBlank()
            mobileError = mobile.isBlank() || !mobile.all { it.isDigit() }
            yearOfBirthError = yearOfBirth.isBlank()

            if (!(userNameError || passwordError || confirmPasswordError || emailError || genderError || mobileError || yearOfBirthError)) {
                val userProfile = UserProfile(userName, password, email, gender, mobile, receiveUpdates, yearOfBirth, "nothing")
                onRegisterSuccess(userProfile)
                println(userProfile)
            }

        },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Register")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
fun RegisterUserScreenPreview() {
    RegisterUserScreen(onRegisterSuccess = {}, onCancel = {})
}