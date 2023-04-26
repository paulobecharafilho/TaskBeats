package com.example.taskbeats

import android.app.Application
import androidx.room.Room
import com.example.taskbeats.data.AppDataBase

class TaskBeatsApplication: Application() {

    //        Criando primeiras instâncias do Room Database.
    private lateinit var database: AppDataBase

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java, "taskbeats-database"
        ).build()
    }

    fun getDatabase(): AppDataBase {
        return database
    }
}