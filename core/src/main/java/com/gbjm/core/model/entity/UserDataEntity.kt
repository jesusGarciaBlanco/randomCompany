package com.gbjm.core.model.entity

data class UserDataEntity (
    val gender: String,
    val name: NameEntity,
    val location: UserLocation,
    val email : String,
    val login : Login,
    val registered: RegisteredDate,
    val phone: String,
    val picture: Pictures

)

data class NameEntity(
    val title: String,
    val first: String,
    val last: String
)

data class UserLocation(
    val city: String,
    val state: String,
    val country: String,
    val postcode: String
)

data class Login(
    val uuid: String
)

data class RegisteredDate(
    val date: String
)

data class Pictures(
    val large: String,
    val medium: String,
    val thumbnail : String
)