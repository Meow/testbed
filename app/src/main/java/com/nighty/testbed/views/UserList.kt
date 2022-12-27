package com.nighty.testbed.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nighty.testbed.MainViewState
import com.nighty.testbed.models.User
import com.nighty.testbed.viewmodels.MainViewModel

@Composable
fun UserList(viewModel: MainViewModel = viewModel()) {
    var pageNumber by remember { mutableStateOf(1L) }
    val users: MutableList<User> = mutableListOf()

    viewModel.loadUsers(onFinish = { userList ->
        users.addAll(userList)
    })

    // todo: pagination

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            viewModel.createRandomUser(onFinish = { usr ->
                usr?.let { users.add(it) }
            })
        }) {
            Text(text = "Generate Random Person")
        }

        val state = viewModel.stateFlow.collectAsState().value

        when (val s = state) {
            MainViewState.Loading -> {
                CircularProgressIndicator()
            }
            is MainViewState.UserCreated -> {
                if (s.usr != null) {
                    Text(text = "Successfully added a random user.")

                    users.add(s.usr)
                } else {
                    Text(text = "Failed to create a random user!")
                }
            }
            is MainViewState.UsersLoaded -> {
                users.clear()
                users.addAll(s.users)

                LazyColumn {
                    items(users.count()) { index ->
                        Button(onClick = {

                        }) {
                            UserListItem(users[index])
                        }
                    }
                }
            }
            else -> {
                Text(text = "Something went wrong!")
            }
        }
    }
}