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
import com.artemissoftware.githubprofile.ui.profile.adapters.SmallRepositoryListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GithubProfileFragment : Fragment(R.layout.fragment_github_profile), GithubProfileContract.View {

    @Inject
    lateinit var getUserProfileUseCase: GetUserProfileUseCase

    private var _binding: FragmentGithubProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: GithubProfilePresenter
    private val pinnedListAdapter by lazy { RepositoryListAdapter() }
    private val starredListAdapter by lazy { SmallRepositoryListAdapter() }
    private val topListAdapter by lazy { SmallRepositoryListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        _binding = FragmentGithubProfileBinding.bind(view)
        binding.lifecycleOwner = this


        binding.rclPinned.apply {
            adapter = pinnedListAdapter
            layoutManager = LinearLayoutManager(requireContext())

        }


        binding.rclTop.apply {
            adapter = topListAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        }

        binding.rclStar.apply {
            adapter = starredListAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        }

        presenter = GithubProfilePresenter(view = this, getUserProfileUseCase);
        presenter.getProfile()
    }

    override fun showUserProfile(userProfile: UserProfile) {

        binding.user = userProfile
        pinnedListAdapter.userProfile = userProfile
        pinnedListAdapter.submitList(userProfile.pinnedRepo)

        topListAdapter.userProfile = userProfile
        topListAdapter.submitList(userProfile.topRepo)

        starredListAdapter.userProfile = userProfile
        starredListAdapter.submitList(userProfile.starRepo)
    }
}