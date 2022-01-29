package com.gbjm.randomcompany.ui.users.mapper

import com.gbjm.core.domain.entity.User
import com.gbjm.randomcompany.ui.users.entity.UiUserRow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UiUserMapper @Inject constructor(
    private val defaultDispatcher: CoroutineDispatcher
) {

    suspend fun mapDomainUsersListToUi(domainUsers: List<User>): List<UiUserRow> {
        return withContext(defaultDispatcher) {
            domainUsers.map {
                mapDomainUserToUi(it)
            }
        }
    }

    suspend fun mapDomainUserToUi(domainUser: User): UiUserRow {
        return UiUserRow(
            id = domainUser.uuid,
            name = domainUser.name,
            surname = domainUser.surname,
            email = domainUser.email,
            image = domainUser.pictures.thumbnail,
            phone = domainUser.phone,
            isFavorite = false
        )
    }
}