package com.artemissoftware.githubprofile.util

object TestProfiles {

    private val PROFILES = listOf<String>("JakeWharton", "ArtemisSoftware", "Apollo GraphQL")
    private const val PROFILE_SELECTED = 0

    val PROFILE = PROFILES.get(PROFILE_SELECTED)
}