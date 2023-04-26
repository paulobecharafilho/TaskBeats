package com.example.taskbeats.presentation

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.taskbeats.R
import com.example.taskbeats.data.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.Serializable

class TaskListActivity : AppCompatActivity() {

    private val adapter = TaskListAdapter(::openTaskDetailView)
    lateinit var emptyLayout: LinearLayout

    private val viewModel: TaskListViewModel by lazy {
        TaskListViewModel.create(application)
    }


    private val resultActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data;
                val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction;

                viewModel.execute(taskAction)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        setSupportActionBar(findViewById(R.id.toolbar))

//        Setando emptyLayout
        emptyLayout = findViewById(R.id.taskList_layoutEmpty)


//      Recuperando RecycleView do XML
        val rvTasks: RecyclerView = findViewById(R.id.rv_taskListRecyclerView);
        rvTasks.adapter = adapter

//        Recuperando Floating Button do xml e setando função
        val fabAdd: FloatingActionButton = findViewById(R.id.tasklist_fabAdd)

//        Function on click FabButton
        fabAdd.setOnClickListener {
            openTaskDetailView(null, null)
        }
    }

    override fun onStart() {
        super.onStart()
        listFromDatabase()
    }

    private fun listFromDatabase() {
        println("Chamando listFromDatabase")

        val listObserver = Observer<List<Task>> {listTasks ->
            if (listTasks.isEmpty()) {
                emptyLayout.visibility = View.VISIBLE
            } else {
                emptyLayout.visibility = View.GONE
            }
            adapter.submitList(listTasks)
        }

        //LiveData
        viewModel.taskListLiveData.observe(this@TaskListActivity, listObserver)
    }

    //    Inflate Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_task_list, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuTaskList_deleteAll -> {
                viewModel.deleteAll()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun openTaskDetailView(task: Task? = null, position: Int? = -1) {
        val intent = TaskDetailActivity.start(this, task, position)
//        Chamar resultActivity
        resultActivity.launch(intent)
    }

}

enum class ActionType {
    CREATE,
    UPDATE,
    DELETE
}

data class TaskAction(
    val task: Task,
    val ActionType: String,
    val position: Int?
) : Serializable

const val TASK_ACTION_RESULT = "TASK_ACTION_RESULT"