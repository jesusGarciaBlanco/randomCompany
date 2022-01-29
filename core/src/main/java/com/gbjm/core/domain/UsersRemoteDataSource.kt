package com.gbjm.core.domain

import androidx.paging.PagingData
import com.gbjm.core.model.entity.UserDataEntity
import kotlinx.coroutines.flow.Flow

interface UsersRemoteDataSource {

    fun getUsers(): Flow<PagingData<UserDataEntity>>
}