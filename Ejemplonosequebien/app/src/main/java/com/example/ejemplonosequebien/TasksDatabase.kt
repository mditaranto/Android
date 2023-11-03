package com.example.ejemplonosequebien

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ejemplonosequebien.TaskDao
import com.example.ejemplonosequebien.entidades.TaskEntity

@Database(entities = arrayOf(TaskEntity::class), version = 1)
abstract class TasksDatabase:RoomDatabase() {
    abstract fun taskDao(): TaskDao

}