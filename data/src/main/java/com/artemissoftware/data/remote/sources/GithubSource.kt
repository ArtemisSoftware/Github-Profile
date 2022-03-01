package com.artemissoftware.data.remote.sources

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.artemissoftware.data.UserQuery
import com.artemissoftware.data.remote.HandleApi.safeApiCall
import javax.inject.Inject

class GithubSource @Inject constructor (private val apolloClient: ApolloClient) {

    suspend fun getUserProfile(name: String): ApolloResponse<UserQuery.Data> {
        return safeApiCall {
            apolloClient.query(UserQuery(name = name)).execute()
        }
    }


}