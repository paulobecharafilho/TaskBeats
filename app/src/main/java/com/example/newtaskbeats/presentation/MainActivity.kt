package com.example.taskbeats.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taskbeats.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val taskListFragment = TaskListFragment.newInstance()
        val newsListFragment = NewsListFragment.newInstance()


    }
}