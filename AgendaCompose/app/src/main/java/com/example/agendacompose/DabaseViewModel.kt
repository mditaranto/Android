package com.example.agendacompose

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.agendacompose.bbdd.*

class DatabaseViewModel(application: Application) : ViewModel() {
    val database: ContactoDatabase by lazy {
        Room.databaseBuilder(
            application,
            ContactoDatabase::class.java,
            "contactoTable"
        ).build()
    }
}