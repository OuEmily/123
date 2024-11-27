package com.emily.todoapp.model

import java.util.Date
import java.util.UUID

data class TodoItem(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val importance: Importance,
    val deadline: Date?,
    val isCompleted: Boolean,
    val createdAt: Date,
    val modifiedAt: Date? = null
)

enum class Importance {
    LOW, NORMAL, HIGH
}


