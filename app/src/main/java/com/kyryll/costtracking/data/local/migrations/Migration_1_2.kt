package com.kyryll.costtracking.data.local.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE TransactionEntity ADD COLUMN byUserId INTEGER DEFAULT 1 NOT NULL")

        db.execSQL(
            """
            CREATE TABLE UserEntity_new (
                userId INTEGER NOT NULL PRIMARY KEY,
                bitcoinRate TEXT NOT NULL,
                userBalance TEXT NOT NULL,
                lastSessionTime TEXT NOT NULL
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            INSERT INTO UserEntity_new (userId, bitcoinRate, userBalance, lastSessionTime)
            SELECT 
                COALESCE(userId, 1),
                bitcoinRate, 
                userBalance, 
                lastSessionTime 
            FROM UserEntity
            """.trimIndent()
        )

        db.execSQL("DROP TABLE UserEntity")

        db.execSQL("ALTER TABLE UserEntity_new RENAME TO UserEntity")
    }
}