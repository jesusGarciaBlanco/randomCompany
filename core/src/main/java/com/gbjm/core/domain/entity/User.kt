package com.gbjm.core.domain.entity

import com.gbjm.core.model.entity.Pictures
import com.gbjm.core.model.entity.UserDataEntity
import java.text.SimpleDateFormat
import java.util.*

data class User(
    val id: String,
    val name: String,
    val surname: String,
    val phone: String,
    val address: String,
    val email: String,
    val registeredDate: Date,
    val pictures: Pictures
)

fun UserDataEntity.toDomain(): User {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val dateParsed = sdf.parse(this.registered.date)
    return User(
        id = login.uuid,
        name = "${name.title} ${name.first}",
        surname =name.last,
        phone = phone,
        address = "${location.country}, ${location.state}, ${location.city}, ${location.postcode}",
        email = email,
        registeredDate = dateParsed,
        pictures = Pictures(picture.large,picture.medium, picture.thumbnail),
    )
}