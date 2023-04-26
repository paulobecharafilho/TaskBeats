package com.example.taskbeats.presentation

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskbeats.TaskBeatsApplication
import com.example.taskbeats.data.Task
import com.example.taskbeats.data.TaskDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskListViewModel(private val taskDao: TaskDao): ViewModel() {

    val taskListLiveData: LiveData<List<Task>> = taskDao.getAll();

    fun execute(taskAction: TaskAction) {
        when (taskAction.ActionType) {
            ActionType.DELETE.name -> deleteTaskFromDatabase(taskAction.task)
            ActionType.CREATE.name -> addTaskToDatabase(taskAction.task)
            ActionType.UPDATE.name -> updateTaskDatabase(taskAction.task)
        }
    }

    //    Add (Insert)
    private fun addTaskToDatabase(task: Task) {
       viewModelScope.launch(Dispatchers.IO) {
           taskDao.insert(task)
       }
    }

    //    Update
    private fun updateTaskDatabase(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.insert(task)
        }
    }

    //    Delete
    private fun deleteTaskFromDatabase(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.delete(task)
        }
    }

    //    Delete All
     fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.deleteAll();
        }
    }


    companion object {
        fun create(application: Application): TaskListViewModel {
            val dataBaseInstance = (application as TaskBeatsApplication).getDatabase()
            val dao = dataBaseInstance.taskDao()
            return TaskListViewModel(dao)
        }
    }
}