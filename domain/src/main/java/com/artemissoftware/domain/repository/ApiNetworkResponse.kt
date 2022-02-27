package com.artemissoftware.domain.repository

data class ApiNetworkResponse<T>(
    val data: T? = null,
    val error: String? = null
)