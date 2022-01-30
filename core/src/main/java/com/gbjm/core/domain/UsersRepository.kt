package com.gbjm.core.domain

import androidx.paging.PagingData
import com.gbjm.core.domain.entity.Favorite
import com.gbjm.core.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    suspend fun getAllUsers() : Flow<PagingData<User>>

    suspend fun deleteUser(id: String)

    suspend fun clearUsers()

    fun getUserDetail(id: String): User

    suspend fun addFavorite(id: String)

    suspend fun getFavorites(): List<Favorite>

    suspend fun deleteFavorite(id: String)
}