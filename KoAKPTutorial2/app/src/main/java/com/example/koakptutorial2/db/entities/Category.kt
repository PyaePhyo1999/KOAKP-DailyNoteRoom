package com.example.koakptutorial2.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "category")

data class Category(
    @PrimaryKey(autoGenerate = true) val id : Int,
    @ColumnInfo(name = "text") val name : String

)

