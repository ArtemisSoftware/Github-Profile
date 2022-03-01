package com.artemissoftware.domain.usecase

import BaseUseCaseTest
import com.artemissoftware.domain.models.Repository
import com.artemissoftware.domain.models.Resource
import com.artemissoftware.domain.models.UserProfile
import com.artemissoftware.domain.repository.ApiNetworkResponse
import com.artemissoftware.domain.repository.GitHubRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*

class GetUserProfileUseCaseTest  : BaseUseCaseTest() {


    private lateinit var gitHubRepository: GitHubRepository
    private lateinit var getUserProfileUseCase: GetUserProfileUseCase


    @Before
    fun setUp() {
        gitHubRepository = mock()
        getUserProfileUseCase = GetUserProfileUseCase(gitHubRepository = gitHubRepository)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `ask for data and receive data`() = runBlockingTest {

        val githubUser = "lopez"

        val userProfile = UserProfile(
            name = "lopes",
            avatarUrl= "lopez.com",
            login= "superlopes",
            email = "email@email",
            followers= 12,
            following= 23,
            pinnedRepo = emptyList<Repository>(),
            starRepo = emptyList<Repository>(),
            topRepo = emptyList<Repository>()
        )

        whenever(gitHubRepository.getUserProfile(githubUser)).thenReturn(
            ApiNetworkResponse(userProfile)
        )

        // Execute the use-case
        val emissions = getUserProfileUseCase.invoke(githubUser).toList()
        var result = (emissions[0] as Resource)
        assert(result is Resource.Loading)
        result = (emissions[1] as Resource)
        assert(result.data?.name == userProfile.name)
    }



    @ExperimentalCoroutinesApi
    @Test
    fun `Should return InvalidParameters error`() = runBlockingTest {

        val githubUser = ""

        val error = GetUserProfileUseCase.INVALID_PARAMETER

        whenever(gitHubRepository.getUserProfile(githubUser)).thenReturn(
            ApiNetworkResponse(error = error)
        )

        // Execute the use-case
        val emissions = getUserProfileUseCase.invoke(githubUser).toList()
        var result = (emissions[0] as Resource)
        assert(result is Resource.Loading)
        result = (emissions[1] as Resource)
        assert(result is Resource.Error)
        assert(result.message == error)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `ask for data and receive cached data because it has not expired`() = runBlockingTest {


        var date = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DATE, 1)
        date = calendar.time

        val githubUser = "lopez"

        val userProfile = UserProfile(
            name = "lopes",
            avatarUrl= "lopez.com",
            login= "superlopes",
            email = "email@email",
            followers= 12,
            following= 23,
            expirationDate = date,
            pinnedRepo = emptyList<Repository>(),
            starRepo = emptyList<Repository>(),
            topRepo = emptyList<Repository>()
        )

        whenever(gitHubRepository.getCachedUserProfile()).thenReturn(
            userProfile
        )

        // Execute the use-case
        val emissions = getUserProfileUseCase.invoke(githubUser).toList()
        var result = (emissions[0] as Resource)
        assert(result is Resource.Loading)
        result = (emissions[1] as Resource)
        assert(result.data?.name == userProfile.name)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `ask for data and receive new data because cached data is expired`() = runBlockingTest {

        var date = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DATE, -1)
        date = calendar.time

        val githubUser = "lopez"

        val userProfileCachedData = UserProfile(
            name = "lopes",
            avatarUrl= "lopez.com",
            login= "superlopes",
            email = "email@email",
            followers= 12,
            following= 23,
            expirationDate = date,
            pinnedRepo = emptyList<Repository>(),
            starRepo = emptyList<Repository>(),
            topRepo = emptyList<Repository>()
        )


        val userProfileNewData = UserProfile(
            name = "lopes23",
            avatarUrl= "lopez.com",
            login= "superlopes",
            followers= 14,
            following= 25,
            email = "email@email",
            expirationDate = date,
            pinnedRepo = emptyList<Repository>(),
            starRepo = emptyList<Repository>(),
            topRepo = emptyList<Repository>()
        )

        whenever(gitHubRepository.getCachedUserProfile()).thenReturn(
            userProfileCachedData
        )
        whenever(gitHubRepository.getUserProfile(githubUser)).thenReturn(
            ApiNetworkResponse(userProfileNewData)
        )

        // Execute the use-case
        val emissions = getUserProfileUseCase.invoke(githubUser).toList()
        var result = (emissions[0] as Resource)
        assert(result is Resource.Loading)
        result = (emissions[1] as Resource)
        assert(result.data?.name == userProfileNewData.name)
    }




    @ExperimentalCoroutinesApi
    @Test
    fun `Should return no internet connection`() = runBlockingTest {

        val githubUser = "lopez"

        whenever(gitHubRepository.getUserProfile(githubUser)).thenReturn(
            ApiNetworkResponse(error = "apollo conction unavailable")
        )

        // Execute the use-case
        val emissions = getUserProfileUseCase.invoke(githubUser).toList()
        var result = (emissions[0] as Resource)
        assert(result is Resource.Loading)
        result = (emissions[1] as Resource)
        assert(result is Resource.Error)
        assert(result.message == "apollo conction unavailable")
    }
}