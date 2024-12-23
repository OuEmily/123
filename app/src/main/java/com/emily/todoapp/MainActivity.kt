package com.emily.todoapp

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.emily.todoapp.adapter.TodoAdapter
import com.emily.todoapp.databinding.ActivityMainBinding
import com.emily.todoapp.ui.AddEditTodoFragment
import com.emily.todoapp.model.TodoItem
import com.emily.todoapp.model.TodoItemsRepository
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            setupRecyclerView()
        } else {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
            if (currentFragment is AddEditTodoFragment) {
                showFragment()
            }
        }

        binding.fabAddTask.setOnClickListener {
            openAddEditTodoFragment(null)
        }

        binding.fabAddTask.setImageResource(R.drawable.icon_plus)
        binding.fabAddTask.imageTintList = null

    }

    private fun setupRecyclerView() {
        val adapter = TodoAdapter(TodoItemsRepository.items.value)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            TodoItemsRepository.items.collect { todoItems ->
                adapter.updateItems(todoItems)
            }
        }

        adapter.setOnItemClickListener { todoItem ->
            openAddEditTodoFragment(todoItem)
        }
    }



    private fun openAddEditTodoFragment(todoItem: TodoItem?) {
        val fragment = AddEditTodoFragment().apply {
            arguments = Bundle().apply {
                putParcelable("todoItem", todoItem)
            }
        }

        showFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun showFragment() {
        binding.recyclerView.visibility = View.GONE
        binding.fragmentContainer.visibility = View.VISIBLE
    }

    private fun showRecyclerView() {
        binding.recyclerView.visibility = View.VISIBLE
        binding.fragmentContainer.visibility = View.GONE
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    override fun onBackPressed() {
        hideKeyboard()
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
        showRecyclerView()
    }
}











