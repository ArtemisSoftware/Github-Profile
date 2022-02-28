package com.artemissoftware.githubprofile.ui

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.artemissoftware.domain.models.Resource
import com.artemissoftware.domain.usecase.GetUserProfileUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class GithubProfilePresenter(
    private val view: GithubProfileContract.View,
    private val getUserProfileUseCase: GetUserProfileUseCase
) : GithubProfileContract.Presenter, CoroutineScope {


    private val job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    override fun getProfile() {

        launch {

            getUserProfileUseCase.invoke("JakeWharton")
                .onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            view.showUserProfile(result.data!!)
                        }
                        is Resource.Error -> {

                        }
                        is Resource.Loading -> {

                        }
                    }
                }.launchIn(this)
        }
    }



}