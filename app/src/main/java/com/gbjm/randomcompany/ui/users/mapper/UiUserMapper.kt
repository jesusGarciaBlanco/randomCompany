package com.gbjm.randomcompany.ui.users.mapper

import com.gbjm.core.domain.entity.Favorite
import com.gbjm.core.domain.entity.User
import com.gbjm.randomcompany.ui.users.entity.UiUserRow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UiUserMapper @Inject constructor(
    private val defaultDispatcher: CoroutineDispatcher
) {

    suspend fun mapDomainUsersListToUi(domainUsers: List<User>, favorites: List<Favorite>): List<UiUserRow> {
        return withContext(defaultDispatcher) {
            domainUsers.map {
                mapDomainUserToUi(it,favorites)
            }
        }
    }

    suspend fun mapDomainUserToUi(domainUser: User, favorites: List<Favorite>): UiUserRow {
        val myMap = favorites.map { it.uuid to it }.toMap()
        return UiUserRow(
            id = domainUser.uuid,
            name = domainUser.name,
            surname = domainUser.surname,
            email = domainUser.email,
            image = domainUser.pictures.thumbnail,
            phone = domainUser.phone,
            isFavorite = myMap.containsKey(domainUser.uuid)
        )
    }
}