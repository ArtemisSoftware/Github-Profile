package com.artemissoftware.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "repository",

    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = arrayOf("name"),
        childColumns = arrayOf("userName"),
        onDelete = ForeignKey.CASCADE
    )]

)
data class RepositoryEntity(
    val userName: String,
    @PrimaryKey val name: String,
    val description: String,
    val stargazerCount: Int,
    val createdAt: Date,
    val languageColor: String,
    val language: String,
    val type: Int
)
