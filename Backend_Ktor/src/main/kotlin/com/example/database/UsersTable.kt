package com.example.database

import org.jetbrains.exposed.dao.id.IntIdTable

object UsersTable : IntIdTable() {
    val name = varchar("name", 100)
    val email = varchar("email", 100).uniqueIndex()
    val password = varchar("password", 255) // Guardará el Hash, no la real
}