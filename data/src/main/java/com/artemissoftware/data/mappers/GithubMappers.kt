package com.artemissoftware.data.mappers

import com.artemissoftware.data.UserLoloQuery
import com.artemissoftware.domain.models.UserProfile

fun UserLoloQuery.Data.toUserProfile(): UserProfile {

    return UserProfile(
        name = user?.name ?: "",
        avatarUrl = user?.avatarUrl.toString() ,
        login = user?.login ?: ""
    )
}

