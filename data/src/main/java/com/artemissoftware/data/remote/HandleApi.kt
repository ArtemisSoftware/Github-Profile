package com.artemissoftware.data.remote

import com.apollographql.apollo3.api.ApolloResponse
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

//                e.printStackTrace()
//                Log.e("BaseRemoteRepo", "Call error: ${e.localizedMessage}", e.cause)
            when(ex){

                is ClassCastException -> throw ex
                is SocketTimeoutException -> throw ex//emitter.onError(ErrorType.TIMEOUT)
                is IOException -> throw ex//emitter.onError(ErrorType.NETWORK)
                else -> throw ex//emitter.onError(ErrorType.UNKNOWN)
            }
        }
    }
}