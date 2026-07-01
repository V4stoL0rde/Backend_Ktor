package com.example.repository

import com.example.database.UsersTable
import com.example.models.RegisterRequest
import com.example.models.User
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {

    private fun rowToUser(row: ResultRow?): User? {
        if (row == null) return null
        return User(
            id = row[UsersTable.id].value,
            name = row[UsersTable.name],
            email = row[UsersTable.email]
        )
    }

    fun findUserByEmail(email: String): ResultRow? {
        return transaction {
            UsersTable.select { UsersTable.email eq email }.singleOrNull()
        }
    }

    fun createUser(user: RegisterRequest, hashedPassword: String): User? {
        return transaction {
            val insertId = UsersTable.insertAndGetId {
                it[name] = user.name
                it[email] = user.email
                it[password] = hashedPassword
            }
            rowToUser(UsersTable.select { UsersTable.id eq insertId }.singleOrNull())
        }
    }
}