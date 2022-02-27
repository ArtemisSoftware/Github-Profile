package com.artemissoftware.data.remote.sources

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.artemissoftware.data.UserLoloQuery
import com.artemissoftware.data.remote.HandleApi.safeApiCall
import javax.inject.Inject

class GithubSource @Inject constructor (private val apolloClient: ApolloClient) {

    suspend fun getUserProfile(name: String): ApolloResponse<UserLoloQuery.Data> {
        return safeApiCall {
            apolloClient.query(UserLoloQuery(name = name)).execute()
        }
    }


}