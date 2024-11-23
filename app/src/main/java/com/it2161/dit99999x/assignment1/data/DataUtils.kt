package com.it2161.dit99999x.assignment1.data


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.it2161.dit99999x.assignment1.data.Comments
import com.it2161.dit99999x.assignment1.data.MovieItem
import com.it2161.dit99999x.assignment1.data.jsonData
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.text.firstOrNull
import kotlin.text.split
import kotlin.text.toFloatOrNull


fun calculateDuration(dateString: String, timeString: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val commentDate = dateFormat.parse("$dateString $timeString")
    val currentDate = Date()
    val diffInMillis = currentDate.time - commentDate.time
    val diffInSeconds = diffInMillis / 1000
    val diffInMinutes = diffInSeconds / 60
    val diffInHours = diffInMinutes / 60
    val diffInDays = diffInHours / 24

    return when {
        diffInDays > 0 -> "$diffInDays days ago"
        diffInHours > 0 -> "$diffInHours hours ago"
        diffInMinutes > 0 -> "$diffInMinutes minutes ago"
        else -> "Just now"
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