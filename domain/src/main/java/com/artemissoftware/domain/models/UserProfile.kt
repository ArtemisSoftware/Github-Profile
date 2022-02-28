package com.artemissoftware.domain.models

import java.util.*

data class UserProfile(
    val name: String,
    val avatarUrl: String,
    val login: String,
    val followers: Int,
    val following: Int,
    val expirationDate: Date? = null ,
    val pinnedRepo: List<Repository>,
    val starRepo: List<Repository>,
    val topRepo: List<Repository>,
    )
