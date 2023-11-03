package com.example.ejemplonosequebien

import android.app.Application
import androidx.room.Room

public class MisNotasApp: Application() {
    companion object {
        lateinit var database: TasksDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database =
            Room.databaseBuilder(this, TasksDatabase::class.java, "tasks-db").build()
    }
}
