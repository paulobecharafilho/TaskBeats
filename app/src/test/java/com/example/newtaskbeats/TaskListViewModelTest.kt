package com.example.taskbeats//package com.example.taskbeats

import com.example.taskbeats.data.TaskDao
import com.example.taskbeats.presentation.TaskListViewModel
import org.junit.Test
import org.mockito.kotlin.mock

class TaskListViewModelTest {

    private val taskDao: TaskDao = mock();

    private val underTest: TaskListViewModel by lazy {
        TaskListViewModel(taskDao)
    }

    @Test
    fun delete_all() {

    }
}