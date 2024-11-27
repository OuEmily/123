package com.emily.todoapp.model

import java.util.*

data class TodoApp(
    val id: String, // Task identifier
    val text: String, // Task text
    val importance: Importance, // Task importance
    val deadline: Date? = null, // Task deadline (optional)
    var isDone: Boolean, // Completion flag
    val createdAt: Date, // Creation date
    val updatedAt: Date? = null // Update date (optional)
)



