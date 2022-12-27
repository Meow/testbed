package com.nighty.testbed.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.nighty.testbed.models.User
import com.nighty.testbed.viewmodels.MainViewModel
import java.time.format.DateTimeFormatter

@Composable
fun User(viewModel: MainViewModel, navController: NavController, user: User) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(128.dp)
        ) {
            AsyncImage(
                model = user.avatarUrl,
                contentDescription = null
            )

            Text(
                "${user.firstName} ${user.lastName}",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.size(32.dp)
            )
        }
        Text(
            "Username: ${user.username}",
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold
        )
        Text("Phone: ${user.phone}", fontStyle = FontStyle.Italic)
        Text(
            "Birthday: ${user.dateOfBirth?.format(DateTimeFormatter.ISO_LOCAL_DATE)}",
            fontStyle = FontStyle.Italic
        )
        Text(
            "Registered on ${user.createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE)}",
            fontStyle = FontStyle.Italic
        )
        Text(
            "Updated on ${user.updatedAt.format(DateTimeFormatter.ISO_LOCAL_DATE)}",
            fontStyle = FontStyle.Italic
        )

        Button(onClick = {
            viewModel.deleteUser(user, onFinish = {
                navController.navigate("user-list")
            })
        }) {
            Text(text = "Delete User")
        }
    }
}