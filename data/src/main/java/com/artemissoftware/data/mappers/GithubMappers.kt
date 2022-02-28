package com.artemissoftware.data.mappers

import com.artemissoftware.data.UserLoloQuery
import com.artemissoftware.data.database.entities.RepositoryEntity
import com.artemissoftware.data.database.entities.UserEntity
import com.artemissoftware.domain.models.Repository
import com.artemissoftware.domain.models.UserProfile
import java.util.*

fun UserLoloQuery.Data.toUserProfile(): UserProfile {

    (user?.pinnedItems?.nodes?.map { it?.toRepository() } ?: emptyList<Repository>())

    return UserProfile(
        name = user?.name ?: "",
        avatarUrl = user?.avatarUrl.toString() ,
        login = user?.login ?: "",
        followers = user?.followers?.totalCount ?: 0,
        following = user?.following?.totalCount ?: 0,
        pinnedRepo = (user?.pinnedItems?.nodes?.map { it?.toRepository() } ?: emptyList<Repository>()) as List<Repository>,
        starRepo = (user?.stars?.nodes?.map { it?.toRepository() } ?: emptyList<Repository>()) as List<Repository>,
        topRepo = (user?.top?.nodes?.map { it?.toRepository() } ?: emptyList<Repository>()) as List<Repository>
    )
}

fun UserLoloQuery.Node.toRepository(): Repository {

    return Repository(
        name = onRepository?.name ?: "",
        description =  onRepository?.description  ?: "",
        stargazerCount = onRepository?.stargazerCount  ?: 0,
        languageColor = onRepository?.languages?.nodes?.get(0)?.color ?: "",
        language = onRepository?.languages?.nodes?.get(0)?.name  ?: "",
    )
}

fun UserLoloQuery.Node2.toRepository(): List<Repository> {


    return emptyList()
}

fun UserLoloQuery.Node4.toRepository(): List<Repository> {


    return emptyList()
}


fun UserLoloQuery.Node3.toRepository(): List<Repository> {


    return emptyList()
}


//fun UserProfile.toUserEntity(): UserEntity {
//
//    return UserEntity(
//        avatarUrl = avatarUrl,
//        name = name,
//        login = login,
//        followers = followers,
//        following = following,
//        expirationDate = Date() + 24H
//    )
//}



//fun UserProfile.toRepositoryEntity(type: Int, userName: String): List<RepositoryEntity> {
//
//
//    return RepositoryEntity(
//        userName = userName,
//        name = String,
//        description = String,
//        stargazerCount = Int,
//        createdAt = Date,
//       languageColor = String,
//        language = String,
//        type = type
//    )
//}