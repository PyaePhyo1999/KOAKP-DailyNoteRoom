package com.example.koakptutorial2.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.koakptutorial2.db.dao.CategoryDao
import com.example.koakptutorial2.db.entities.Todo
import com.example.koakptutorial2.db.dao.TodoDao
import com.example.koakptutorial2.db.entities.Category

@Database(entities = [Todo::class,Category::class],version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao() : TodoDao
    abstract fun categoryDao() : CategoryDao
}