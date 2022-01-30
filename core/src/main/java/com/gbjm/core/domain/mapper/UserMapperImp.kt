package com.gbjm.core.domain.mapper

import com.gbjm.core.domain.entity.User
import com.gbjm.core.model.entity.Pictures
import com.gbjm.core.model.entity.UserDataEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import javax.inject.Inject

class UserMapperImp @Inject constructor(
    private val defaultDispatcher: CoroutineDispatcher
) {

    suspend fun mapRemoteUsersListToDomain(
        remoteUsers: List<UserDataEntity>
    ): List<User> {
        return withContext(defaultDispatcher) {
            remoteUsers.map {
                mapRemoteUserToDomain(it)
            }
        }
    }

    suspend fun mapRemoteUserToDomain(
        remoteUser: UserDataEntity
    ): User {
        return User(
            uuid = remoteUser.login.uuid,
            name = "${remoteUser.name.title} ${remoteUser.name.first}",
            surname =remoteUser.name.last,
            phone = remoteUser.phone,
            address = "${remoteUser.location.country}, ${remoteUser.location.state}, ${remoteUser.location.city}, ${remoteUser.location.postcode}",
            email = remoteUser.email,
            registeredDate = remoteUser.registered.date,
            pictures = Pictures(remoteUser.picture.large,remoteUser.picture.medium, remoteUser.picture.thumbnail),
            gender = remoteUser.gender
        )
    }
}