package com.example.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.github.cdimascio.dotenv.dotenv
import java.util.Date

object JwtConfig {
    private val env = dotenv()
    private val secret = env["JWT_SECRET"] ?: "secret"
    private val issuer = env["JWT_ISSUER"] ?: "habitos"
    private const val VALIDITY_IN_MS = 36_000_000 // 10 horas

    fun generateToken(email: String): String {
        return JWT.create()
            .withIssuer(issuer)
            .withClaim("email", email)
            .withExpiresAt(Date(System.currentTimeMillis() + VALIDITY_IN_MS))
            .sign(Algorithm.HMAC256(secret))
    }
}