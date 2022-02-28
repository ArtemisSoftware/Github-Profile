package com.artemissoftware.data.database.dao

import androidx.room.*
import com.artemissoftware.data.database.entities.UserEntity
import com.artemissoftware.data.database.relations.UserAndRepositories

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    @Query("DELETE FROM user")
    suspend fun deleteUsers()

    @Transaction
    @Query("SELECT * FROM user")
    suspend fun getUserAndRepositories(): List<UserAndRepositories>
}