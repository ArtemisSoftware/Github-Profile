package com.artemissoftware.domain.repository

import com.artemissoftware.domain.models.UserProfile

interface GitHubRepository {

    suspend fun getUserProfile(): ApiNetworkResponse<UserProfile>

}