package com.example.koakptutorial2.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "todo", foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Todo(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "categoryId") val categoryId: Int
)

