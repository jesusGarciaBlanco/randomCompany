package com.gbjm.randomcompany.ui.detail.mapper

import com.gbjm.core.domain.entity.User
import com.gbjm.core.domain.entity.toDomain
import com.gbjm.randomcompany.ui.detail.entity.UiUserDetail
import com.gbjm.randomcompany.ui.users.entity.UiUserRow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class UiUserDetailMapper @Inject constructor(
    private val defaultDispatcher: CoroutineDispatcher
) {

    fun mapDomainUserToUiDetail(domainUser: User): UiUserDetail {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val dateParsed = sdf.parse(domainUser.registeredDate)
        var formattedDate = SimpleDateFormat("dd-MM-yyyy").format(dateParsed)
            return UiUserDetail(
                id = domainUser.uuid,
                image = domainUser.pictures.large,
                name = domainUser.name,
                surname = domainUser.surname,
                email = domainUser.email,
                gender = domainUser.gender,
                location = domainUser.address,
                registeredDate = formattedDate
            )

    }
}