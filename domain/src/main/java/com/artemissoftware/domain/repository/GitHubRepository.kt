package com.artemissoftware.domain.repository

import com.artemissoftware.domain.models.UserProfile

interface GitHubRepository {

    suspend fun getUserProfile(name: String): ApiNetworkResponse<UserProfile>

    suspend fun deleteCache()

    suspend fun getCachedUserProfile() : UserProfile
}