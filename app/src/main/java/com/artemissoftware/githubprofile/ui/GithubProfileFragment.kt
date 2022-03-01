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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.artemissoftware.domain.usecase.RefreshUserProfileUseCase
import com.google.android.material.snackbar.Snackbar




@AndroidEntryPoint
class GithubProfileFragment : Fragment(R.layout.fragment_github_profile), GithubProfileContract.View {

    @Inject
    lateinit var getUserProfileUseCase: GetUserProfileUseCase

    @Inject
    lateinit var refreshUserProfileUseCase: RefreshUserProfileUseCase


    private var _binding: FragmentGithubProfileBinding? = null
    private val binding get() = _binding!!

//    private lateinit var presenter: GithubProfilePresenter
    private val pinnedListAdapter by lazy { RepositoryListAdapter() }
    private val starredListAdapter by lazy { SmallRepositoryListAdapter() }
    private val topListAdapter by lazy { SmallRepositoryListAdapter() }


    @Inject
    lateinit var presenter: GithubProfilePresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        _binding = FragmentGithubProfileBinding.bind(view)
        binding.lifecycleOwner = this
        presenter.view = this

        init()

       presenter.getProfile()
    }

    private fun init() {
        binding.swipeContainer.setOnRefreshListener {
            presenter.refreshProfile()
        }

        binding.swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

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
    }


    override fun showUserProfile(userProfile: UserProfile) {

        binding.user = userProfile
        pinnedListAdapter.userProfile = userProfile
        topListAdapter.userProfile = userProfile
        starredListAdapter.userProfile = userProfile

        binding.swipeContainer.isRefreshing = false

        activity?.runOnUiThread {
            pinnedListAdapter.submitList(userProfile.pinnedRepo)
            topListAdapter.submitList(userProfile.topRepo)
            starredListAdapter.submitList(userProfile.starRepo)
        }
    }

    override fun showError(message: String) {
        val snackbar =
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
        snackbar.show()

        binding.swipeContainer.isRefreshing = false
    }

    override fun showLoading(show: Boolean) {
        activity?.runOnUiThread {
            binding.progressBar.visibility = if(show) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}