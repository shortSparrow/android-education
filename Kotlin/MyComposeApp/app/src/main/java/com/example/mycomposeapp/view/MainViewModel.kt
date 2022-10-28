package com.example.mycomposeapp.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _list = getTempList().toMutableStateList()
    val list: List<TaskItem>
        get() = _list

    fun removeTask(task: TaskItem) {
        _list.remove(task)
    }

    fun toggleCheck(task: TaskItem, checked: Boolean) {
        _list.find { it.id == task.id }?.let { task ->
            task.checked = checked
        }
    }
}

fun getTempList() = List(1000) { TaskItem(id = it, label = "Do this: $it") }

data class TaskItem(val id: Int, val label: String,) {
    var checked by mutableStateOf(false)
}