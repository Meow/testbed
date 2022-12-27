package com.nighty.testbed.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nighty.testbed.AppDatabase
import com.nighty.testbed.MainViewState
import com.nighty.testbed.api.ApplicationApi
import com.nighty.testbed.models.User
import io.ktor.client.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

class MainViewModel(client: HttpClient, private val db: AppDatabase) : ViewModel() {
    private val internalStateFlow = MutableStateFlow<MainViewState>(MainViewState.Ready(""))
    private val api = ApplicationApi(client)
    val stateFlow: StateFlow<MainViewState> = internalStateFlow

    fun loadLoremIpsum(count: Int, onFinish: (response: String) -> Unit) {
        viewModelScope.launch {
            internalStateFlow.value = MainViewState.Loading
            val response = api.loremIpsum(count)
            internalStateFlow.value = MainViewState.Ready(response)
        }
    }

    fun loadUsers(p: Long = 1, perPage: Long = 50, onFinish: (users: List<User>) -> Unit) {
        viewModelScope.launch {
            internalStateFlow.value = MainViewState.Loading
            val users = db.userDao().page(p, perPage)
            internalStateFlow.value = MainViewState.UsersLoaded(users)
        }
    }

    fun countUsers(): Long {
        return db.userDao().count()
    }

    fun createUser(
        username: String,
        firstName: String? = null,
        lastName: String? = null,
        avatarUrl: String? = null,
        phone: String? = null,
        dob: OffsetDateTime? = null
    ): User? {
        return db.userDao().findById(
            db.userDao().insert(
                User(
                    0L,
                    username,
                    firstName,
                    lastName,
                    avatarUrl,
                    phone,
                    dob,
                    OffsetDateTime.now(),
                    OffsetDateTime.now()
                )
            )
        )
    }

    fun deleteUser(usr: User, onFinish: () -> Unit) {
        viewModelScope.launch {
            internalStateFlow.value = MainViewState.Loading
            db.userDao().delete(usr)
            internalStateFlow.value = MainViewState.UserDeleted
        }
    }

    fun createRandomUser(onFinish: (usr: User?) -> Unit) {
        viewModelScope.launch {
            internalStateFlow.value = MainViewState.Loading

            val userdata = api.randomUser()
            val user = createUser(
                userdata.username,
                userdata.first_name,
                userdata.last_name,
                userdata.avatar,
                userdata.phone_number,
                OffsetDateTime.parse("${userdata.date_of_birth}T00:00:00+00:00")
            )

            internalStateFlow.value = MainViewState.UserCreated(
                user
            )
        }
    }
}
