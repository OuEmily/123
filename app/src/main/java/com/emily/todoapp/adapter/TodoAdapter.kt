package com.emily.todoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emily.todoapp.R
import com.emily.todoapp.model.TodoItem

class TodoAdapter(private var items: List<TodoItem>) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val statusIcon: ImageView = view.findViewById(R.id.statusIcon)
        val taskText: TextView = view.findViewById(R.id.taskText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = items[position]

        holder.taskText.text = item.text

        // Логика выбора иконки статуса задач
        val iconRes = when {
            item.isCompleted -> R.drawable.ic_task_completed
            item.deadline != null && item.deadline.before(java.util.Date()) -> R.drawable.ic_task_deadline
            else -> R.drawable.ic_task_not_completed
        }
        holder.statusIcon.setImageResource(iconRes)
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<TodoItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}






