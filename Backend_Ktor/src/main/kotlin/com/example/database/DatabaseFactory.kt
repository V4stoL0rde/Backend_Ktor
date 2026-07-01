package com.example.database

import io.github.cdimascio.dotenv.dotenv
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val env = dotenv()
        val config = HikariConfig().apply {
            jdbcUrl = env["DB_URL"]
            username = env["DB_USER"]
            password = env["DB_PASSWORD"]
            driverClassName = "org.postgresql.Driver"
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        }
        val dataSource = HikariDataSource(config)
        Database.connect(dataSource)

        // Crea las tablas automáticamente en Neon si no existen
        transaction {
            SchemaUtils.create(UsersTable)
        }
    }
}