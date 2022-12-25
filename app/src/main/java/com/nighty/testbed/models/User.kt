package com.nighty.testbed.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "users")
data class User(
    @ColumnInfo(name = "id") @PrimaryKey val id: Int,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "first_name") val firstName: String? = "No name provided",
    @ColumnInfo(name = "first_name") val lastName: String? = "No last name provided",
    @ColumnInfo(name = "avatar") val avatarUrl: String? = null,
    @ColumnInfo(name = "phone") val phone: String? = "No phone number provided",
    @ColumnInfo(name = "date_of_birth") val dateOfBirth: OffsetDateTime? = null,
    @ColumnInfo(name = "created_at") val createdAt: OffsetDateTime,
    @ColumnInfo(name = "updated_at") val updatedAt: OffsetDateTime,
)
