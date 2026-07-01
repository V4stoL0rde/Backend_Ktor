package com.example.routes

import com.example.models.LoginRequest
import com.example.models.RegisterRequest
import com.example.models.TokenResponse
import com.example.security.JwtConfig
import com.example.service.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.authRoutes() {
    val userService = UserService()

    post("/register") {
        val request = call.receive<RegisterRequest>()
        val user = userService.register(request)
        if (user != null) {
            call.respond(HttpStatusCode.Created, user)
        } else {
            call.respond(HttpStatusCode.BadRequest, "El usuario ya existe")
        }
    }

    post("/login") {
        val request = call.receive<LoginRequest>()
        val user = userService.verifyLogin(request)
        if (user != null) {
            val token = JwtConfig.generateToken(user.email)
            call.respond(HttpStatusCode.OK, TokenResponse(token))
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Credenciales incorrectas")
        }
    }
}