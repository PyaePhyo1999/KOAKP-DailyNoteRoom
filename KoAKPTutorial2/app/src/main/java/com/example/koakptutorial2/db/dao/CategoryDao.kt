package com.example.koakptutorial2.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.koakptutorial2.db.entities.Category
import com.example.koakptutorial2.db.entities.Todo

@Dao
interface CategoryDao{
    @Query("SELECT * FROM category")
    fun selectAll(): List<Category>

    @Query("SELECT * FROM category WHERE id= :categoryId ")
    fun getCategoryId(categoryId: Int):Category

    @Insert
    fun insert(category: Category)

    @Delete
    fun delete(category: Category)
}