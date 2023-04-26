package com.example.taskbeats.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.taskbeats.R
import com.example.taskbeats.data.Task

class TaskDetailActivity : AppCompatActivity() {

    private var task: Task? = null
    private var position: Int = -1

    //    Passando a responsabilidade de criar a intent para a própria intent a ser aberta porque qualquer página pode chamar.
    companion object {
        private const val TASK_DETAIL_EXTRA = "task.detail.extra"
        private const val TASK_DETAIL_POSITION_EXTRA = "task.detail.position.extra"

        fun start(context: Context, task: Task?, position: Int?): Intent {
            val intent = Intent(context, TaskDetailActivity::class.java)
                .apply {
                    putExtra(TASK_DETAIL_EXTRA, task)
                    putExtra(TASK_DETAIL_POSITION_EXTRA, position)
                }
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        setSupportActionBar(findViewById(R.id.toolbar))

//        Recuperando valor passado pela intent anterior ao chamar esta:
        task = intent.getSerializableExtra(TASK_DETAIL_EXTRA) as Task?
        position = intent.getIntExtra(TASK_DETAIL_POSITION_EXTRA, -1)

//        Caputando Ids das Activities
        val edtTitle: EditText = findViewById(R.id.taskDetail_editTitle)
        val edtDescription: EditText = findViewById(R.id.taskDetail_editDescription)
        val btnConfirm: Button = findViewById(R.id.taskDetail_btnConfirm)

//        Mostrando em tela caso uma task venha passada pela intent
        if (task != null) {
            edtTitle.setText(task!!.title)
            edtDescription.setText(task!!.description)
            btnConfirm.text = "Atualizar Task"
        }

        btnConfirm.setOnClickListener {
            val title = edtTitle.text.toString()
            val description = edtDescription.text.toString()


            if (title.isNotEmpty() && description.isNotEmpty()) {
                if (task == null) {
                    addNewTask(title, description)
                } else {
                    updateTask(title, description)
                }
            } else Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show()
        }
    }

//    Function to Create Options Button
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_task_detail, menu)
        if (task == null && menu != null) {
            menu.findItem(R.id.menuTaskDetail_deleteTask).isVisible = false
        }

        return true
    }




//    Function to Return an Action
    private fun returnAction(task: Task, actionType: ActionType) {
        val intent = Intent()
            .apply {
                val taskAction = TaskAction(task, actionType.name, position)
                putExtra(TASK_ACTION_RESULT, taskAction)
            }
        setResult(Activity.RESULT_OK, intent)
        println("Função chamada -> ${actionType.name} na position -> $position e com task -> $task")
        finish()
    }


//    Functions of CRUD

//    Add new Task
    private fun addNewTask(title: String, description: String) {

        val task = Task(title=title, description = description)
        val actionType = ActionType.CREATE

        returnAction(task, actionType)
}

//    Delete Task
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuTaskDetail_deleteTask -> {
                val actionType: ActionType = ActionType.DELETE
                returnAction(task!!, actionType)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

//    Update Taks
    private fun updateTask(title: String, description: String) {
        val newTask = Task(task!!.id, title, description)
        val actionType: ActionType = ActionType.UPDATE

        returnAction(newTask, actionType)
    }

}