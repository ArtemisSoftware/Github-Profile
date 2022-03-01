package com.artemissoftware.githubprofile.ui

import com.artemissoftware.domain.models.UserProfile

interface GithubProfileContract {

    interface View {
        fun showUserProfile(userProfile: UserProfile)
        fun showError(message: String)
        fun showLoading(show: Boolean)
    }

    interface Presenter {
        fun getProfile()
        fun refreshProfile()
    }
}