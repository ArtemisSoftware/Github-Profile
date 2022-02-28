package com.artemissoftware.githubprofile.ui

import com.artemissoftware.domain.models.UserProfile

interface GithubProfileContract {

    interface View {
        fun showUserProfile(userProfile: UserProfile)
    }

    interface Presenter {
        fun getProfile()
    }
}