package com.emily.todoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emily.todoapp.R
import com.emily.todoapp.model.TodoItem
import com.emily.todoapp.model.Importance
import com.emily.todoapp.model.TodoItemsRepository

class TodoAdapter(private var items: List<TodoItem>) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private var onItemClickListener: ((TodoItem) -> Unit)? = null

    // Интерфейс для обработки кликов
    fun setOnItemClickListener(listener: (TodoItem) -> Unit) {
        onItemClickListener = listener
    }

    class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val statusIcon: ImageView = view.findViewById(R.id.statusIcon)
        val taskText: TextView = view.findViewById(R.id.taskText)
        val checkBox: ImageView = view.findViewById(R.id.statusIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = items[position]

        holder.taskText.text = item.text

        // Логика для отображения приоритета задачи
        val iconRes = when (item.importance) {
            Importance.HIGH -> R.drawable.ic_task_deadline // Иконка для высокого приоритета
            Importance.LOW -> R.drawable.ic_task_not_completed  // Иконка для низкого приоритета
            Importance.NONE -> R.drawable.ic_task_not_completed  // Иконка для задачи без приоритета
        }
        holder.statusIcon.setImageResource(iconRes)

// Логика для отображения чекбокса
// Если задача выполнена, показываем зелёную галочку. Если не выполнена, показываем иконку, зависящую от приоритета.
        val checkBoxRes = if (item.isCompleted) {
            R.drawable.ic_task_completed // Иконка галочки для выполненной задачи
        } else {
            // Если задача не выполнена, показываем иконку, которая зависит от важности задачи
            when (item.importance) {
                Importance.HIGH -> R.drawable.ic_task_deadline  // Для высокого приоритета
                Importance.LOW -> R.drawable.ic_task_not_completed  // Для низкого приоритета
                Importance.NONE -> R.drawable.ic_task_not_completed  // Для задачи без приоритета
            }
        }
        holder.checkBox.setImageResource(checkBoxRes)


        // Обработка клика по чекбоксу
        holder.checkBox.setOnClickListener {
            // Изменяем состояние isCompleted
            val updatedItem = item.copy(
                isCompleted = !item.isCompleted,
                importance = if (item.isCompleted) item.importance else Importance.NONE  // Если выполнено, приоритет NONE
            )

            // Обновляем задачу в репозитории
            TodoItemsRepository.updateTodoItem(updatedItem)

            // Уведомляем адаптер, что задача была изменена
            items = items.toMutableList().apply { set(position, updatedItem) }  // Обновляем элемент в списке
            notifyItemChanged(position)  // Уведомляем адаптер, что этот элемент изменился
        }

        // Обработка клика по элементу (например, для редактирования задачи)
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(item)
        }
    }


    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<TodoItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}















