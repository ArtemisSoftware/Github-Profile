package com.artemissoftware.domain.usecase

import com.artemissoftware.domain.errors.ApiDataException
import com.artemissoftware.domain.models.Resource
import com.artemissoftware.domain.models.UserProfile
import com.artemissoftware.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(private val gitHubRepository: GitHubRepository) {

    operator fun invoke(name : String): Flow<Resource<UserProfile>> = flow {

        val apiResult = gitHubRepository.getUserProfile(name = name)

        gitHubRepository.deleteCache()

        apiResult.data?.let {




            emit(Resource.Success(it))

        } ?: run{
            throw ApiDataException(apiResult.error)
        }

    }

}