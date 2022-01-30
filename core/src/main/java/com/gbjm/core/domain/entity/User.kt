package com.gbjm.core.domain.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gbjm.core.model.entity.Pictures
import com.gbjm.core.model.entity.UserDataEntity
import java.text.SimpleDateFormat

@Entity(tableName = "users")
data class User(
    @PrimaryKey val uuid: String,
    val name: String,
    val surname: String,
    val phone: String,
    val address: String,
    val email: String,
    val registeredDate: String,
    @Embedded
    val pictures: Pictures,
    val gender: String
)

fun UserDataEntity.toDomain(): User {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val dateParsed = sdf.parse(this.registered.date)
    return User(
        uuid = login.uuid,
        name = "${name.title} ${name.first}",
        surname =name.last,
        phone = phone,
        address = "${location.country}, ${location.state}, ${location.city}, ${location.postcode}",
        email = email,
        registeredDate = registered.date,
        pictures = Pictures(picture.large,picture.medium, picture.thumbnail),
        gender = gender
    )
}