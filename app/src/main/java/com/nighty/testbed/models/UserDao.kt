package com.nighty.testbed.models

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Insert
    fun insertMany(vararg users: User)

    @Update
    fun update(user: User)

    @Update
    fun updateMany(vararg users: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM users u ORDER BY u.created_at ASC")
    fun all(): List<User>

    @Query("SELECT * FROM users u ORDER BY u.created_at ASC LIMIT 1")
    fun first(): User?

    @Query("SELECT * FROM users u ORDER BY u.created_at DESC LIMIT 1")
    fun last(): User?

    @Query("SELECT * FROM users u WHERE u.id = :id LIMIT 1")
    fun findById(id: Int): User?

    @Query("SELECT * FROM users u WHERE u.username = :username LIMIT 1")
    fun findByUsername(username: String): User?
}
