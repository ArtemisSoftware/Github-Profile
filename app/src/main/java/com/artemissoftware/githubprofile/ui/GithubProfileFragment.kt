package com.artemissoftware.githubprofile.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.artemissoftware.domain.models.UserProfile
import com.artemissoftware.domain.usecase.GetUserProfileUseCase
import com.artemissoftware.githubprofile.R
import com.artemissoftware.githubprofile.databinding.FragmentGithubProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GithubProfileFragment : Fragment(R.layout.fragment_github_profile), GithubProfileContract.View {

    @Inject
    lateinit var getUserProfileUseCase: GetUserProfileUseCase

    private var _binding: FragmentGithubProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: GithubProfilePresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        _binding = FragmentGithubProfileBinding.bind(view)
        binding.lifecycleOwner = this

        presenter = GithubProfilePresenter(view = this, getUserProfileUseCase);
        presenter.getProfile()
    }

    override fun showUserProfile(userProfile: UserProfile) {

        binding.user = userProfile
    }
}