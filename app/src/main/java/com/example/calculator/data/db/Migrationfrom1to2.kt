package com.example.calculator.data.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Migrationfrom1to2() : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE history_item_entity ADD COLUMN createdAt TEXT DEFAULT \"${
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            }\" NOT NULL"
        )
    }

}