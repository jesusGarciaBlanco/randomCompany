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
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val dateParsed = sdf.parse(remoteUser.registered.date)
        return User(
            id = remoteUser.login.uuid,
            name = "${remoteUser.name.title} ${remoteUser.name.first}",
            surname =remoteUser.name.last,
            phone = remoteUser.phone,
            address = "${remoteUser.location.country}, ${remoteUser.location.state}, ${remoteUser.location.city}, ${remoteUser.location.postcode}",
            email = remoteUser.email,
            registeredDate = dateParsed,
            pictures = Pictures(remoteUser.picture.large,remoteUser.picture.medium, remoteUser.picture.thumbnail),
        )
    }

//    override suspend fun mapDomainUsersListToUi(domainUsers: List<User>): List<UiUserRow> {
//        return withContext(defaultDispatcher) {
//            domainUsers.map {
//                mapDomainUserToUi(it)
//            }
//        }
//    }
//
//    override suspend fun mapDomainUserToUi(domainUser: User): UiUserRow {
//        return UiUserRow(
//            id = domainUser.id,
//            name = domainUser.name,
//            surname = domainUser.surname,
//            email = domainUser.email,
//            image = domainUser.pictures.thumbnail,
//            phone = domainUser.phone,
//            isFavorite = false
//        )
//    }
}