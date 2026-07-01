package com.example.service

import com.example.models.LoginRequest
import com.example.models.RegisterRequest
import com.example.models.User
import com.example.repository.UserRepository
import com.example.database.UsersTable
import org.mindrot.jbcrypt.BCrypt

class UserService {
    private val repository = UserRepository()

    fun register(request: RegisterRequest): User? {
        // Encriptamos la contraseña con BCrypt
        val hashedPassword = BCrypt.hashpw(request.password, BCrypt.gensalt())
        return repository.createUser(request, hashedPassword)
    }

    fun verifyLogin(request: LoginRequest): User? {
        val row = repository.findUserByEmail(request.email) ?: return null
        val storedHash = row[UsersTable.password]

        // Compara la contraseña limpia con el Hash guardado
        if (BCrypt.checkpw(request.password, storedHash)) {
            return User(row[UsersTable.id].value, row[UsersTable.name], row[UsersTable.email])
        }
        return null
    }
}