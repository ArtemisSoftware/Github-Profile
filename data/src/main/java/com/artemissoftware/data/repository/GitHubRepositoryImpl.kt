package com.artemissoftware.data.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.artemissoftware.data.UserLoloQuery
import com.artemissoftware.data.errors.GithubProfileApiNetworkException
import com.artemissoftware.data.mappers.toUserProfile
import com.artemissoftware.data.remote.sources.GithubSource
import com.artemissoftware.domain.models.UserProfile
import com.artemissoftware.domain.repository.ApiNetworkResponse
import com.artemissoftware.domain.repository.GitHubRepository
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor (
    private val githubSource: GithubSource
    ):
    GitHubRepository {

    override suspend fun getUserProfile(name : String): ApiNetworkResponse<UserProfile> {

        return try {

            val result: ApolloResponse<UserLoloQuery.Data> = githubSource.getUserProfile(name = name)


            result.data?.let {
                ApiNetworkResponse(it.toUserProfile())
            } ?: run{
                ApiNetworkResponse(error = "No data found")
            }

        } catch (ex: GithubProfileApiNetworkException) {
            ApiNetworkResponse(error = ex.message)
        }
    }

    override suspend fun deleteCache() {
        TODO("Not yet implemented")
    }


}