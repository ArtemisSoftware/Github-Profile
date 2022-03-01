package com.artemissoftware.githubprofile.ui

import com.artemissoftware.domain.models.Resource
import com.artemissoftware.domain.usecase.GetUserProfileUseCase
import com.artemissoftware.domain.usecase.RefreshUserProfileUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class GithubProfilePresenter @Inject constructor(
    //private val view: GithubProfileContract.View,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val refreshUserProfileUseCase: RefreshUserProfileUseCase
) : GithubProfileContract.Presenter, CoroutineScope {


    private val job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    var view: GithubProfileContract.View? = null

    override fun getProfile() {

        launch {

            getUserProfileUseCase.invoke("JakeWharton")
                .onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            view?.showUserProfile(result.data!!)
                        }
                        is Resource.Error -> {
                            view?.showError(result.message!!)
                        }
                    }

                    view?.showLoading(result is Resource.Loading)

                }.launchIn(this)
        }
    }

    override fun refreshProfile() {

        launch {

            refreshUserProfileUseCase.invoke("JakeWharton")
                .onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            view?.showUserProfile(result.data!!)
                        }
                        is Resource.Error -> {
                            view?.showError(result.message!!)
                        }
                    }

                    view?.showLoading(result is Resource.Loading)

                }.launchIn(this)
        }
    }

}