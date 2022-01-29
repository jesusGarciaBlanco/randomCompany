package com.gbjm.randomcompany.ui.users.entity

data class UiUserRow(
    val id: String,
    val name: String,
    val surname: String,
    val email: String,
    val image: String,
    val phone: String,
    var isFavorite: Boolean
)
