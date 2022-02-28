package com.artemissoftware.data.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.artemissoftware.data.database.entities.RepositoryEntity
import com.artemissoftware.data.database.entities.UserEntity

data class UserAndRepositories (

    @Embedded
    val userEntity: UserEntity,

    @Relation(
        parentColumn = "name",
        entityColumn = "userName"
    )
    val repositories: List<RepositoryEntity>
)