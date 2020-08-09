package com.example.koakptutorial2.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.koakptutorial2.db.entities.Category
import com.example.koakptutorial2.db.entities.Todo

@Dao
interface TodoDao{
    @Query("SELECT * FROM todo")
    fun selectAll(): List<Todo>

    @Query("SELECT * FROM todo WHERE categoryId= :categoryId")
    fun selectCategoryId(categoryId : Int): List<Todo>

    @Insert
    fun insert(todo: Todo)

    @Delete
    fun delete(todo : Todo)
}