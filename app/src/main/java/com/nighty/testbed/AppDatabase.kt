package com.nighty.testbed

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nighty.testbed.models.User
import com.nighty.testbed.models.UserDao

@Database(version = 1, entities = [User::class], exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}