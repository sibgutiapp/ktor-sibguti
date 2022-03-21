package com.example

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val userId: Int,
    val email: String,
    val avatarUrl: String,
    val displayName: String
)

