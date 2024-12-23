package com.emily.todoapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class TodoItem(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    var importance: Importance = Importance.NONE,
    val deadline: Date? = null,
    var isCompleted: Boolean = false,
    val createdAt: Date = Date(),
    val modifiedAt: Date? = null
) : Parcelable

enum class Importance {
    LOW, NONE, HIGH
}



