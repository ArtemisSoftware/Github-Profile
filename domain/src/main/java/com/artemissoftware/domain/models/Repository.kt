package com.artemissoftware.domain.models

import java.util.*

data class Repository(

    val name: String,
    val description: String,
    val stargazerCount: Int,
    val languageColor : String,
    val language: String,
)
