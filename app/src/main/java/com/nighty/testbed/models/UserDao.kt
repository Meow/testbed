package com.nighty.testbed.models

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    fun insert(user: User): Long

    @Insert
    fun insertMany(vararg users: User): List<Long>

    @Update
    fun update(user: User)

    @Update
    fun updateMany(vararg users: User)

    @Delete
    fun delete(user: User)

    // Why do I have to implement my own query DSL?
    // What kind of trash is this ORM??
    // Ok fine here, have some very basic ActiveRecord-like methods.

    @Query("SELECT * FROM users u ORDER BY datetime(u.created_at) ASC")
    fun all(): List<User>

    @Query("SELECT COUNT(*) FROM users")
    fun count(): Long

    @Query("SELECT * FROM users u ORDER BY datetime(u.created_at) ASC LIMIT 1")
    fun first(): User?

    @Query("SELECT * FROM users u ORDER BY datetime(u.created_at) DESC LIMIT 1")
    fun last(): User?

    @Query("SELECT * FROM users u ORDER BY datetime(u.created_at) DESC LIMIT :perPage OFFSET :p * :perPage - :perPage")
    fun page(p: Long = 1, perPage: Long = 50): List<User>

    @Query("SELECT * FROM users u WHERE u.id = :id LIMIT 1")
    fun findById(id: Long): User?

    @Query("SELECT * FROM users u WHERE u.username = :username LIMIT 1")
    fun findByUsername(username: String): User?

    // Apparently Google's implementation of sqlite3 does not
    // support `TRUNCATE TABLE` command. We are forced to use
    // `DELETE FROM table` instead.
    //
    // I am just putting it for the record here, I would've used
    // `TRUNCATE TABLE` instead, as it would have been much faster
    // and safer.
    @Query("DELETE FROM users")
    fun deleteAll()
}
