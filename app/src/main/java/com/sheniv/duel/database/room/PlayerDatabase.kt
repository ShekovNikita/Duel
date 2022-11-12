package com.sheniv.duel.database.room

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    exportSchema = true,
    version = 1,
    entities = [Player::class],
    /*autoMigrations = [
        AutoMigration (from = 1, to = 2)
    ]*/
)
abstract class PlayerDatabase : RoomDatabase() {

    abstract fun todoDao(): PlayerDatabaseDao
}