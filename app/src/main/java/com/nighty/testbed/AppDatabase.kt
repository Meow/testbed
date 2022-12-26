package com.nighty.testbed

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nighty.testbed.models.User
import com.nighty.testbed.models.UserDao

@Database(version = 1, entities = [User::class], exportSchema = true)
@TypeConverters(AppTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}