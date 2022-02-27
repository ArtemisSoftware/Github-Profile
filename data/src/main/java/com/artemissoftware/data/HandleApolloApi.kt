package com.artemissoftware.data

import com.apollographql.apollo3.api.ApolloResponse
import okio.IOException
import java.net.SocketTimeoutException

object HandleApolloApi {

    suspend fun <T> safeApiCall(callFunction: suspend () -> T): T {
        return try{

            val response = callFunction.invoke()

//            val dd = response as ApolloResponse<*>
//
//            dd.

            response
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