package com.artemissoftware.domain.usecase

import com.artemissoftware.domain.errors.ApiDataException
import com.artemissoftware.domain.models.Resource
import com.artemissoftware.domain.models.UserProfile
import com.artemissoftware.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(private val gitHubRepository: GitHubRepository) {

    operator fun invoke(name : String): Flow<Resource<UserProfile>> = flow {

        emit(Resource.Loading())

        val userProfile = gitHubRepository.getCachedUserProfile()

        if(requestData(userProfile)){

            if(name.isBlank()){
                emit(Resource.Error<UserProfile>(message = INVALID_PARAMETER))
            }
            else{
                val apiResult = gitHubRepository.getUserProfile(name = name)

                apiResult.data?.let {

                    gitHubRepository.refreshCache(it)
                    emit(Resource.Success(it))

                } ?: run{
                    emit(Resource.Error<UserProfile>(message = apiResult.error!!))
                }
            }
        }
        else{
            emit(Resource.Success(userProfile!!))
        }
    }

    private fun requestData(userProfile: UserProfile?): Boolean{
        return userProfile?.expirationDate?.after(Date())?.not() ?: true
    }


    companion object{

        const val INVALID_PARAMETER = "Invalid parameter"
    }
}