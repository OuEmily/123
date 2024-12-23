package com.emily.todoapp.ui

import android.os.Bundle
import android.app.DatePickerDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.emily.todoapp.databinding.FragmentAddEditTodoBinding
import com.emily.todoapp.model.Importance
import com.emily.todoapp.model.TodoItem
import com.emily.todoapp.R
import com.emily.todoapp.model.TodoItemsRepository
import java.text.SimpleDateFormat
import java.util.*

class AddEditTodoFragment : Fragment() {

    private var _binding: FragmentAddEditTodoBinding? = null
    private val binding get() = _binding!!

    private var todoItem: TodoItem? = null
    private var isEditMode = false

    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddEditTodoBinding.inflate(inflater, container, false)

        // Получаем задачу, если это редактирование
        todoItem = arguments?.getParcelable("todoItem")
        isEditMode = todoItem != null

        // Если задача редактируется, заполняем поля
        if (isEditMode) {
            todoItem?.let {
                binding.edtTaskDescription.setText(it.text)

                // Устанавливаем выбранный приоритет в Spinner
                val priorityIndex = it.importance.ordinal
                binding.spnPriority.setSelection(priorityIndex)

                // Если есть дата дедлайна, отображаем её
                it.deadline?.let { deadline ->
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    binding.edtDeadline.setText(dateFormat.format(deadline))
                }
            }
        }

        // Настройка выпадающего списка приоритетов
        val priorityAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.importance_options,
            android.R.layout.simple_spinner_item
        )
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnPriority.adapter = priorityAdapter

        // Обработчик клика по дедлайну (выбор даты)
        binding.edtDeadline.setOnClickListener {
            showDatePickerDialog()
        }

        // Обработчик кнопки "Сохранить"
        binding.btnSave.setOnClickListener {
            saveTodoItem()
        }

        // Обработчик кнопки "Закрыть"
        binding.btnClose.setOnClickListener {
            requireActivity().onBackPressed() // Вернуться назад
        }

        // Если задача редактируется, показываем кнопку "Удалить"
        if (isEditMode) {
            binding.btnDelete.visibility = View.VISIBLE
        }

        // Обработчик кнопки "Удалить"
        binding.btnDelete.setOnClickListener {
            todoItem?.let {
                deleteTask(it) // Удалить задачу
            }
        }

        return binding.root
    }

    private fun updateDateInView() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.edtDeadline.setText(dateFormat.format(calendar.time))
    }

    private fun showDatePickerDialog() {
        val dateListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                updateDateInView()
            }

        DatePickerDialog(
            requireContext(),
            dateListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    // Функция сохранения или обновления задачи
    private fun saveTodoItem() {
        val text = binding.edtTaskDescription.text.toString()
        val deadline = calendar.time.takeIf { binding.edtDeadline.text.isNotEmpty() }

        // Получаем выбранный приоритет
        val priority = Importance.values()[binding.spnPriority.selectedItemPosition]

        // Если задача редактируется, обновляем её
        if (isEditMode && todoItem != null) {
            val updatedItem = todoItem?.copy(
                text = text,
                deadline = deadline,
                importance = priority // Обновляем приоритет
            )
            updatedItem?.let {
                // Здесь вызываем обновление задачи в репозитории
                TodoItemsRepository.updateTodoItem(it)
            }
        } else {
            // Если новая задача, создаём её
            val newItem = TodoItem(
                id = UUID.randomUUID().toString(),
                text = text,
                importance = priority, // Устанавливаем приоритет
                deadline = deadline,
                isCompleted = false,
                createdAt = Date(),
                modifiedAt = null
            )
            TodoItemsRepository.addTodoItem(newItem)
        }

        requireActivity().onBackPressed() // Вернуться назад
    }

    private fun deleteTask(task: TodoItem) {
        // Логика для удаления задачи из репозитория или базы данных
        TodoItemsRepository.deleteTodoItem(task)
        requireActivity().onBackPressed() // Закрыть фрагмент после удаления
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}













