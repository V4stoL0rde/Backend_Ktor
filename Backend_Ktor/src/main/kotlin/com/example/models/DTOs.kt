package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class User(val id: Int, val name: String, val email: String) // Sin password por seguridad

@Serializable
data class RegisterRequest(val name: String, val email: String, val password: String)

@Serializable
data class LoginRequest(val email: String, val password: String)

@Serializable
data class TokenResponse(val token: String)