package com.artemissoftware.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "user")
data class UserEntity(

    val avatarUrl: String,
    @PrimaryKey val name: String,
    val login: String,
    val followers: Int,
    val following: Int,
    val expirationDate: Date
)
