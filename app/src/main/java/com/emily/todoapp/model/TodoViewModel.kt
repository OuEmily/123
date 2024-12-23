package com.emily.todoapp.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emily.todoapp.model.TodoItem
import com.emily.todoapp.model.TodoItemsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {

    // Создаем LiveData для списка задач
    private val _todoItems = MutableLiveData<List<TodoItem>>()
    val todoItems: LiveData<List<TodoItem>> get() = _todoItems

    init {
        // Подписываемся на изменения в репозитории
        viewModelScope.launch {
            TodoItemsRepository.items.collect { todoItems ->
                _todoItems.value = todoItems
            }
        }
    }

    fun updateTodoItem(updatedItem: TodoItem) {
        // Обновление задачи
        TodoItemsRepository.updateTodoItem(updatedItem)
    }

    fun addTodoItem(newItem: TodoItem) {
        // Добавление новой задачи
        TodoItemsRepository.addTodoItem(newItem)
    }

    fun deleteTodoItem(todoItem: TodoItem) {
        // Удаление задачи
        TodoItemsRepository.deleteTodoItem(todoItem)
    }
}


