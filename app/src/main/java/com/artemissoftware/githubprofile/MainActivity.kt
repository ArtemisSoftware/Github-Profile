package com.artemissoftware.githubprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.artemissoftware.data.ff
import com.artemissoftware.data.remote.sources.GithubSource
import com.artemissoftware.data.repository.GitHubRepositoryImpl
import com.artemissoftware.domain.models.Resource
import com.artemissoftware.domain.usecase.GetUserProfileUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var getUserProfileUseCase: GetUserProfileUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        lifecycleScope.launchWhenResumed {


            lifecycleScope.launch {

                getUserProfileUseCase.invoke("JakeWharton")
                    .onEach { result ->
                        when(result) {
                            is Resource.Success -> {
                                Log.d("LaunchList", "Success ${result.data}")
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


}