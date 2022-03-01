package com.artemissoftware.domain.usecase

import BaseUseCaseTest
import com.artemissoftware.domain.models.Repository
import com.artemissoftware.domain.models.Resource
import com.artemissoftware.domain.models.UserProfile
import com.artemissoftware.domain.repository.ApiNetworkResponse
import com.artemissoftware.domain.repository.GitHubRepository
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*

class RefreshUserProfileUseCaseTest  : BaseUseCaseTest() {


    private lateinit var gitHubRepository: GitHubRepository
    private lateinit var refreshUserProfileUseCase: RefreshUserProfileUseCase


    @Before
    fun setUp() {
        gitHubRepository = mock()
        refreshUserProfileUseCase = RefreshUserProfileUseCase(gitHubRepository = gitHubRepository)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `ask for data refresh and receive new data`() = runBlockingTest {

        val githubUser = "lopez"

        val userProfile = UserProfile(
            name = "lopes",
            avatarUrl = "lopez.com",
            login = "superlopes",
            followers = 12,
            following = 23,
            pinnedRepo = emptyList<Repository>(),
            starRepo = emptyList<Repository>(),
            topRepo = emptyList<Repository>()
        )

        whenever(gitHubRepository.getUserProfile(githubUser)).thenReturn(
            ApiNetworkResponse(userProfile)
        )

        // Execute the use-case
        val emissions = refreshUserProfileUseCase.invoke(githubUser).toList()
        var result = (emissions[0] as Resource)
        assert(result.data?.name == userProfile.name)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `ask for data refresh with no internet connection and receive cached data`() = runBlockingTest {

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
            followers= 12,
            following= 23,
            expirationDate = date,
            pinnedRepo = emptyList<Repository>(),
            starRepo = emptyList<Repository>(),
            topRepo = emptyList<Repository>()
        )

        var error = "internet connection"

        whenever(gitHubRepository.getCachedUserProfile()).thenReturn(
            userProfileCachedData
        )

        whenever(gitHubRepository.getUserProfile(githubUser)).thenReturn(
            ApiNetworkResponse(error = error)
        )

        // Execute the use-case
        val emissions = refreshUserProfileUseCase.invoke(githubUser).toList()
        var result = (emissions[0] as Resource)
        assert(result.data?.name == userProfileCachedData.name)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Should return no internet connection`() = runBlockingTest {

        val githubUser = "lopez"

        whenever(gitHubRepository.getUserProfile(githubUser)).thenReturn(
            ApiNetworkResponse(error = "apollo conection unavailable")
        )

        // Execute the use-case
        val emissions = refreshUserProfileUseCase.invoke(githubUser).toList()
        var result = (emissions[0] as Resource)
        assert(result is Resource.Error)
        assert(result.message == "apollo conection unavailable")
    }
}