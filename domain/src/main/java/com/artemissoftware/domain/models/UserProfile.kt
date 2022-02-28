package com.artemissoftware.domain.models

data class UserProfile(
    val name: String,
    val avatarUrl: String,
    val login: String,
    val followers: Int,
    val following: Int,

    val pinnedRepo: List<Repository>,
    val starRepo: List<Repository>,
    val topRepo: List<Repository>,
    )
