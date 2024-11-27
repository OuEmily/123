package com.emily.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.emily.todoapp.adapter.TodoAdapter
import com.emily.todoapp.databinding.ActivityMainBinding
import com.emily.todoapp.model.TodoItemsRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Настройка RecyclerView
        val adapter = TodoAdapter(TodoItemsRepository.items.value)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Наблюдение за изменениями в TodoItemsRepository
        lifecycleScope.launch {
            TodoItemsRepository.getTodoFlow().collect { todoItems ->
                adapter.updateItems(todoItems)
            }
        }


        // Обработка нажатия на кнопку добавления
        binding.fabAddTask.setOnClickListener {
            // Здесь будет переход на экран добавления задачи
        }
    }
}






