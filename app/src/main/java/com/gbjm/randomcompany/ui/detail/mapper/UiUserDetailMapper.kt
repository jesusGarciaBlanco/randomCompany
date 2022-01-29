package com.gbjm.randomcompany.ui.detail.mapper

import com.gbjm.core.domain.entity.User
import com.gbjm.randomcompany.ui.detail.entity.UiUserDetail
import com.gbjm.randomcompany.ui.users.entity.UiUserRow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UiUserDetailMapper @Inject constructor(
    private val defaultDispatcher: CoroutineDispatcher
) {

    fun mapDomainUserToUiDetail(domainUser: User): UiUserDetail {
//        return withContext(defaultDispatcher) {
            return UiUserDetail(
                id = domainUser.uuid,
                image = domainUser.pictures.large,
                name = domainUser.name,
                surname = domainUser.surname,
                email = domainUser.email,
                gender = domainUser.gender,
                location = domainUser.address,
                registeredDate = domainUser.registeredDate
            )
//        }

    }
}