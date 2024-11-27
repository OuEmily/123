package com.emily.todoapp.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Date

object TodoItemsRepository {
    private val _items = MutableStateFlow<List<TodoItem>>(emptyList())
    val items: StateFlow<List<TodoItem>> = _items

    init {
        // Список тестовых задач
        val initialItems = listOf(
            TodoItem(
                id = "1",
                text = "Купить продукты",
                importance = Importance.NORMAL,
                deadline = Date(System.currentTimeMillis() + 86400000), // Завтра
                isCompleted = false,
                createdAt = Date(),
                modifiedAt = null
            ),
            TodoItem(
                id = "2",
                text = "Сделать домашнее задание",
                importance = Importance.HIGH,
                deadline = Date(System.currentTimeMillis() - 86400000), // Вчера (просрочено)
                isCompleted = false,
                createdAt = Date(),
                modifiedAt = Date()
            ),
            TodoItem(
                id = "3",
                text = "Прочитать книгу",
                importance = Importance.LOW,
                deadline = null, // Без дедлайна
                isCompleted = true, // Выполнено
                createdAt = Date(),
                modifiedAt = null
            )
        )
        _items.value = initialItems
    }

    // Добавление задачи
    fun addTodoItem(todoItem: TodoItem) {
        val currentList = _items.value.toMutableList()
        currentList.add(todoItem)
        _items.value = currentList
    }

    // Обновление задачи
    fun updateTodoItem(updatedItem: TodoItem) {
        val currentList = _items.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == updatedItem.id }
        if (index != -1) {
            currentList[index] = updatedItem
            _items.value = currentList
        }
    }

    fun getTodoFlow(): StateFlow<List<TodoItem>> {
        return items
    }
}






