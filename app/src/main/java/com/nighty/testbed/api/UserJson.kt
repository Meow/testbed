package com.nighty.testbed.api

import kotlinx.serialization.Serializable

@Serializable
data class UserJson(
    val username: String,
    val first_name: String,
    val last_name: String,
    val phone_number: String,
    val avatar: String,
    val date_of_birth: String,
)
