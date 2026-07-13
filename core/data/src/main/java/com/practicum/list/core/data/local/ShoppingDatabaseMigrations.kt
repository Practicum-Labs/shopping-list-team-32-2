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

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS products_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                listId INTEGER NOT NULL,
                name TEXT NOT NULL,
                quantity REAL NOT NULL DEFAULT 1,
                isChecked INTEGER NOT NULL DEFAULT 0,
                unit TEXT NOT NULL DEFAULT 'pcs',
                sortPosition INTEGER NOT NULL DEFAULT 0,
                FOREIGN KEY(listId) REFERENCES shopping_lists(id) ON DELETE CASCADE
            )
            """.trimIndent(),
        )
        db.execSQL(
            """
            INSERT INTO products_new (id, listId, name, quantity, isChecked, unit, sortPosition)
            SELECT id, listId, name, CAST(quantity AS REAL), isChecked, 'pcs', 0
            FROM products
            """.trimIndent(),
        )
        db.execSQL("DROP TABLE products")
        db.execSQL("ALTER TABLE products_new RENAME TO products")
        db.execSQL("CREATE INDEX IF NOT EXISTS index_products_listId ON products(listId)")
        db.execSQL(
            "UPDATE products SET unit = 'pcs' WHERE unit IS NULL OR unit = ''",
        )
    }
}

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "UPDATE products SET unit = 'pcs' WHERE unit IS NULL OR unit = ''",
        )
    }
}
