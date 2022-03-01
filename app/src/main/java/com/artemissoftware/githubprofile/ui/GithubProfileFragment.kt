package com.artemissoftware.githubprofile.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.artemissoftware.domain.models.UserProfile
import com.artemissoftware.domain.usecase.GetUserProfileUseCase
import com.artemissoftware.githubprofile.R
import com.artemissoftware.githubprofile.databinding.FragmentGithubProfileBinding
import com.artemissoftware.githubprofile.ui.profile.adapters.RepositoryListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GithubProfileFragment : Fragment(R.layout.fragment_github_profile), GithubProfileContract.View {

    @Inject
    lateinit var getUserProfileUseCase: GetUserProfileUseCase

    private var _binding: FragmentGithubProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: GithubProfilePresenter
    private val listAdapter by lazy { RepositoryListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        _binding = FragmentGithubProfileBinding.bind(view)
        binding.lifecycleOwner = this


        binding.rclPinned.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())

        }

        presenter = GithubProfilePresenter(view = this, getUserProfileUseCase);
        presenter.getProfile()
    }

    override fun showUserProfile(userProfile: UserProfile) {

        binding.user = userProfile
        listAdapter.userProfile = userProfile
        listAdapter.submitList(userProfile.pinnedRepo)
    }
}