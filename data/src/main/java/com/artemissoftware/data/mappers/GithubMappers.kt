package com.artemissoftware.data.mappers

import com.artemissoftware.data.UserQuery
import com.artemissoftware.data.database.entities.RepositoryEntity
import com.artemissoftware.data.database.entities.UserEntity
import com.artemissoftware.data.database.relations.UserAndRepositories
import com.artemissoftware.domain.models.Repository
import com.artemissoftware.domain.models.UserProfile
import java.util.*


fun UserQuery.Data.toUserProfile(): UserProfile {

    return UserProfile(
        name = user?.name ?: "",
        avatarUrl = user?.avatarUrl.toString() ,
        login = user?.login ?: "",
        email = user?.email ?: "",
        followers = user?.followers?.totalCount ?: 0,
        following = user?.following?.totalCount ?: 0,
        pinnedRepo = (user?.pinnedItems?.nodes?.map { it?.toRepository() } ?: emptyList<Repository>()) as List<Repository>,
        starRepo = (user?.stars?.nodes?.map { it?.toRepository() } ?: emptyList<Repository>()) as List<Repository>,
        topRepo = (user?.top?.nodes?.map { it?.toRepository() } ?: emptyList<Repository>()) as List<Repository>
    )
}

fun UserQuery.Node.toRepository(): Repository {

    return Repository(
        name = onRepository?.name ?: "",
        description =  onRepository?.description  ?: "",
        stargazerCount = onRepository?.stargazerCount  ?: 0,
        languageColor = onRepository?.languages?.nodes?.get(0)?.color ?: "",
        language = onRepository?.languages?.nodes?.get(0)?.name  ?: "",
    )
}

fun UserQuery.Node2.toRepository(): Repository {

    return Repository(
        name = name,
        description =  description  ?: "",
        stargazerCount = stargazerCount,
        languageColor = languages?.nodes?.get(0)?.color ?: "",
        language = languages?.nodes?.get(0)?.name  ?: "",
    )
}



fun UserQuery.Node4.toRepository(): Repository {

    return Repository(
        name = name,
        description =  description  ?: "",
        stargazerCount = stargazerCount,
        languageColor = languages?.nodes?.get(0)?.color ?: "",
        language = languages?.nodes?.get(0)?.name  ?: "",
    )
}


fun UserAndRepositories.toUserProfile(): UserProfile{

    return UserProfile(
        name = this.userEntity.name,
        avatarUrl = this.userEntity.avatarUrl,
        login = this.userEntity.login,
        email = this.userEntity.email,
        followers = this.userEntity.followers,
        following = this.userEntity.following,
        expirationDate = this.userEntity.expirationDate,
        pinnedRepo = this.repositories.toRespositories(1),
        starRepo = this.repositories.toRespositories(2),
        topRepo = this.repositories.toRespositories(3)
    )
}

fun List<RepositoryEntity>.toRespositories(type: Int): List<Repository>{
    return this.filter { it.type == type }.map { it.toRepository() }
}

fun RepositoryEntity.toRepository(): Repository{
    return Repository(
        name = name,
        description = description,
        stargazerCount = stargazerCount,
        languageColor = languageColor,
        language = language,
    )
}


fun UserProfile.toUserEntity(): UserEntity{

    var date = Date()
    val calendar = Calendar.getInstance()
    calendar.time = date
    calendar.add(Calendar.DATE, 1)
    date = calendar.time

    return UserEntity(
        email = email,
        name = name,
        avatarUrl = avatarUrl,
        login = login,
        followers = followers,
        following = following,
        expirationDate = date
    )
}

fun UserProfile.toRepositorysEntities(name : String): List<RepositoryEntity>{

    var list = mutableListOf<RepositoryEntity>()
    list.addAll(this.pinnedRepo.map { it.toRepositoryEntity(name, 1) })
    list.addAll(this.starRepo.map { it.toRepositoryEntity(name, 2) })
    list.addAll(this.topRepo.map { it.toRepositoryEntity(name, 3) })
    return list
}

fun Repository.toRepositoryEntity(name : String, type: Int): RepositoryEntity{

    return RepositoryEntity(
        userName = name,
        name = this.name,
        description =  description,
        stargazerCount =  stargazerCount,
        languageColor =  languageColor,
        language =  language,
        type = type
    )

}

