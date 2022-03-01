package com.artemissoftware.githubprofile.util

import com.artemissoftware.githubprofile.BuildConfig

object ApiConstants {

        const val BASE_URL = "https://api.github.com/graphql"
        const val GITHUB_PROFILE_KEY = "Bearer " + BuildConfig.GITHUB_PROFILE_KEY

}