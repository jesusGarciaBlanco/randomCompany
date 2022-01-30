package com.gbjm.core.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite (
    @PrimaryKey val uuid: String
)