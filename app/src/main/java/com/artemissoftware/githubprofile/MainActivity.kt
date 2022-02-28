package com.artemissoftware.githubprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.artemissoftware.data.ff
import com.artemissoftware.data.remote.sources.GithubSource
import com.artemissoftware.data.repository.GitHubRepositoryImpl
import com.artemissoftware.domain.models.Resource
import com.artemissoftware.domain.usecase.GetUserProfileUseCase
import com.artemissoftware.githubprofile.ui.GithubProfileFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openFragment(GithubProfileFragment());
    }


    fun openFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}