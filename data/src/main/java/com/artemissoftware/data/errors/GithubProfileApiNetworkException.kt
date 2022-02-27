package com.artemissoftware.data.errors

data class GithubProfileApiNetworkException(override val message: String): RuntimeException()