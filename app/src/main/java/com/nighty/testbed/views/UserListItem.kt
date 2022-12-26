package com.nighty.testbed.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nighty.testbed.models.User
import java.time.format.DateTimeFormatter

@Composable
fun UserListItem(user: User) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(106.dp)
    ) {
        AsyncImage(
            model = user.avatarUrl,
            contentDescription = null
        )
        Column(modifier = Modifier.padding(8.dp)) {
            Text("${user.firstName} ${user.lastName}", fontWeight = FontWeight.Bold)
            Text("Phone ${user.phone}", fontStyle = FontStyle.Italic)
            Text(
                "Birthday ${user.dateOfBirth?.format(DateTimeFormatter.ISO_LOCAL_DATE)}",
                fontStyle = FontStyle.Italic
            )
            Text(
                "Registered on ${user.createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE)}",
                fontStyle = FontStyle.Italic
            )
        }
    }
}