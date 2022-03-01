package com.artemissoftware.domain.usecase

import com.artemissoftware.domain.errors.ApiDataException
import com.artemissoftware.domain.models.Resource
import com.artemissoftware.domain.models.UserProfile
import com.artemissoftware.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefreshUserProfileUseCase @Inject constructor(private val gitHubRepository: GitHubRepository) {

    operator fun invoke(name : String): Flow<Resource<UserProfile>> = flow {

        val userProfile = gitHubRepository.getCachedUserProfile()

        val apiResult = gitHubRepository.getUserProfile(name = name)

        apiResult.data?.let {

            gitHubRepository.refreshCache(it)
            emit(Resource.Success(it))

        } ?: run{

            if(userProfile != null){
                emit(Resource.Success(userProfile))
            }
            else{
                emit(Resource.Error<UserProfile>(message = apiResult.error!!))
            }
            //--throw ApiDataException(apiResult.error)
        }

    }
}