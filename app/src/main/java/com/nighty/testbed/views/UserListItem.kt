package com.nighty.testbed.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.nighty.testbed.models.User

@Composable
fun UserListItem(user: User) {
    Text(user.username)
}