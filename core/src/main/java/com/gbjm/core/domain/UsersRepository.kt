package com.gbjm.core.domain

import androidx.paging.PagingData
import com.gbjm.core.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    suspend fun getAllUsers() : Flow<PagingData<User>>

    fun getUserDetail(id: String): User

    suspend fun getFavorites(): List<String>
}