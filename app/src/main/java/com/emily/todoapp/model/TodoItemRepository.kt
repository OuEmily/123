package com.emily.todoapp.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*

object TodoItemsRepository {
    private val _items = MutableStateFlow<List<TodoItem>>(emptyList())
    val items: StateFlow<List<TodoItem>> = _items

    init {
        // Пример начальных задач
        _items.value = listOf(
            // Выполненное дело
            TodoItem(
                id = "1",
                text = "Прочитать книгу на тему развития навыков общения.",
                importance = Importance.LOW,
                deadline = null, // Без дедлайна
                isCompleted = true,
                createdAt = Date(System.currentTimeMillis() - 86400000 * 2), // Создано 2 дня назад
                modifiedAt = null
            ),
            // Невыполненное дело
            TodoItem(
                id = "2",
                text = "Купить продукты для ужина: картофель, молоко, курица.",
                importance = Importance.NONE,
                deadline = Date(System.currentTimeMillis() + 86400000), // Завтра
                isCompleted = false,
                createdAt = Date(),
                modifiedAt = null
            ),
            // Дело с дедлайном
            TodoItem(
                id = "3",
                text = "Закончить отчёт по работе. Срок сдачи - пятница.",
                importance = Importance.HIGH,
                deadline = Date(System.currentTimeMillis() + 86400000 * 3), // Через 3 дня
                isCompleted = false,
                createdAt = Date(System.currentTimeMillis() - 86400000), // Создано вчера
                modifiedAt = Date(System.currentTimeMillis() - 43200000) // Изменено 12 часов назад
            ),
            // Дело без дедлайна
            TodoItem(
                id = "4",
                text = "Разобраться с настройкой сервера для нового проекта.",
                importance = Importance.NONE,
                deadline = null,
                isCompleted = false,
                createdAt = Date(),
                modifiedAt = null
            ),
            // Дело с длинным текстом (> 3 строк)
            TodoItem(
                id = "5",
                text = """
                    Составить список идей для новогодних подарков:
                    1. Игрушки для детей.
                    2. Книги и учебные материалы.
                    3. Интересные и полезные гаджеты.
                    4. Тёплые вещи: шарфы, перчатки, свитера.
                """.trimIndent(),
                importance = Importance.LOW,
                deadline = null,
                isCompleted = false,
                createdAt = Date(System.currentTimeMillis() - 86400000 * 5), // Создано 5 дней назад
                modifiedAt = null
            ),
            // Дело с высокой важностью
            TodoItem(
                id = "6",
                text = "Записаться на приём к врачу, пока не стало поздно.",
                importance = Importance.HIGH,
                deadline = Date(System.currentTimeMillis() + 86400000 * 2), // Через 2 дня
                isCompleted = false,
                createdAt = Date(),
                modifiedAt = Date()
            )
        )
    }

    fun addTodoItem(todoItem: TodoItem) {
        _items.value = _items.value + todoItem
    }

    fun updateTodoItem(updatedItem: TodoItem) {
        _items.value = _items.value.map { if (it.id == updatedItem.id) updatedItem else it }
    }

    fun deleteTodoItem(todoItem: TodoItem) {
        _items.value = _items.value.filter { it.id != todoItem.id }
    }
}










