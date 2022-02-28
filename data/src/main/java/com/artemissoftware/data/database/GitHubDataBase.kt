package com.artemissoftware.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.artemissoftware.data.database.dao.RepositoryDao
import com.artemissoftware.data.database.dao.UserDao
import com.artemissoftware.data.database.entities.RepositoryEntity
import com.artemissoftware.data.database.entities.UserEntity

@Database(
    entities = [UserEntity::class, RepositoryEntity::class],
    version = 1
)
abstract class GitHubDataBase: RoomDatabase() {

    abstract val userDao: UserDao
    abstract val repositoryDao: RepositoryDao
}
