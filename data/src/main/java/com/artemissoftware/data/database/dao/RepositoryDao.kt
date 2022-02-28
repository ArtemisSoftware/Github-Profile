package com.artemissoftware.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.artemissoftware.data.database.entities.RepositoryEntity
import com.artemissoftware.data.database.entities.UserEntity

@Dao
interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(repositories: List<RepositoryEntity>)
}