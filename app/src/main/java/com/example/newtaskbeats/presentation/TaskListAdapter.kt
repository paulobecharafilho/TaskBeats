package com.example.taskbeats.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskbeats.R
import com.example.taskbeats.data.Task

class TaskListAdapter (
    private val openTaskDetailView: (task: Task, position: Int) -> Unit
) : ListAdapter<Task, TaskListViewHolder>(TaskListAdapter)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.task_item, parent, false)
        return TaskListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task, position, openTaskDetailView)
    }


    companion object : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.description == newItem.description
        }

    }
}


class TaskListViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    private val tvTaskTitle: TextView = view.findViewById(R.id.taskItem_tvTitle)
    private val tvTaskDescription: TextView = view.findViewById(R.id.taskItem_tvDescription);

    fun bind(task: Task, position: Int, openTaskDetailView: (task: Task, position: Int) -> Unit) {


//      Inserindo os valores das Tasks no tasklist
        tvTaskTitle.text = task.title
        tvTaskDescription.text = task.description

        view.setOnClickListener {
            openTaskDetailView.invoke(task, position)
        }
    }
}