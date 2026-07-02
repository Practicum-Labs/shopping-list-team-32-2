package com.practicum.list.core.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "ALTER TABLE shopping_lists ADD COLUMN icon_res_id INTEGER NOT NULL DEFAULT 0",
        )
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "ALTER TABLE shopping_lists ADD COLUMN user_id INTEGER NOT NULL DEFAULT 0",
        )
        db.execSQL(
            "CREATE INDEX IF NOT EXISTS index_shopping_lists_user_id ON shopping_lists(user_id)",
        )
    }
}
