package com.gbjm.core.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gbjm.core.api.ApiService
import com.gbjm.core.model.entity.UserDataEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val NETWORK_PAGE_SIZE = 10

class UsersRemoteDataSourceImp @Inject constructor(
    private val usersService: ApiService
) : UsersRemoteDataSource {

    override fun getUsers(): Flow<PagingData<UserDataEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UsersPagingSource(service = usersService)
            }
        ).flow
    }
}