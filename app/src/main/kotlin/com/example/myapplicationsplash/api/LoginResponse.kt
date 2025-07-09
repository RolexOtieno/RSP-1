package com.example.myapplicationsplash.api


data class LoginResponse(
    val success: Boolean,
    val message: String,
    val token: String? = null
)
