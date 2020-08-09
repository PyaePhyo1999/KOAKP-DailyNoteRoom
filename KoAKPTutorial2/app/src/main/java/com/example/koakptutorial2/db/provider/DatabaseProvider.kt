package com.example.koakptutorial2.db.provider

import android.content.Context
import androidx.room.Room
import com.example.koakptutorial2.db.TodoDatabase


object DatabaseProvider{
    private var database : TodoDatabase? = null
    fun instance(context : Context) : TodoDatabase {
        if (database ==null) {
            database = Room.databaseBuilder(context, TodoDatabase::class.java, "todo.db")
                .fallbackToDestructiveMigration() .build()
        }
        return database!!
    }

}