package com.gbjm.core.domain

import androidx.paging.*
import com.gbjm.core.api.ApiService
import com.gbjm.core.domain.entity.User
import com.gbjm.core.domain.mapper.UserMapperImp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val NETWORK_PAGE_SIZE = 10

class UsersRepositoryImp @Inject constructor(
    private val usersApi: ApiService,
    private val db: AppDatabase,
    private val userMapper: UserMapperImp
) : UsersRepository {

    private val pagingSourceFactory = { db.userDao.getUsers() }

    var favorites = mutableListOf<String>()

    @ExperimentalPagingApi
    override suspend fun getAllUsers(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = CustomRemoteMediator(db, usersApi,userMapper),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }


//    override suspend fun getAllUsers(): Flow<PagingData<User>> {
//        val users = remoteDataSource.getUsers()
//        return remoteDataSource.getUsers().map { pagingData ->
//            pagingData.map { remoteUser ->
//                userMapper.mapRemoteUserToDomain(remoteUser)
//            }
//        }
//    }

    override suspend fun getFavorites(): List<String> {
        return favorites
    }
}