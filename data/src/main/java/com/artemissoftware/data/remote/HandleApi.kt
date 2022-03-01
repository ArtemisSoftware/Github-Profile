package com.artemissoftware.data.remote

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.ApolloNetworkException
import com.artemissoftware.data.errors.GithubProfileApiNetworkException
import okio.IOException
import java.net.SocketTimeoutException

object HandleApi {

    suspend fun <T> safeApiCall(callFunction: suspend () -> T): T {
        return try{

            val apiResponse = callFunction.invoke()

            with(apiResponse as ApolloResponse<*>) {

                if(hasErrors()){
                    this.errors?.get(0)?.let { throw GithubProfileApiNetworkException(it.message) }
                }
            }

            apiResponse
        }
        catch (ex: Exception){

            when(ex){

                is ApolloNetworkException -> throw GithubProfileApiNetworkException(ex.message.toString())
                is ClassCastException -> throw GithubProfileApiNetworkException(ex.message.toString())
                is SocketTimeoutException -> throw GithubProfileApiNetworkException(ex.message.toString())
                is IOException -> throw GithubProfileApiNetworkException(ex.message.toString())
                else -> throw GithubProfileApiNetworkException(ex.message.toString())
            }
        }
    }
}