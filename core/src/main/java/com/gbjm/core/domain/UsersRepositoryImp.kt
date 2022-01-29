package com.gbjm.core.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.gbjm.core.domain.entity.User
import com.gbjm.core.domain.mapper.UserMapperImp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UsersRepositoryImp @Inject constructor(private val remoteDataSource: UsersRemoteDataSource,
                                             private val userMapper: UserMapperImp
) : UsersRepository {

    var favorites = mutableListOf<String>()

    override suspend fun getAllUsers(): Flow<PagingData<User>> {
        val users = remoteDataSource.getUsers()
        return remoteDataSource.getUsers().map { pagingData ->
            pagingData.map { remoteUser ->
                userMapper.mapRemoteUserToDomain(remoteUser)
            }
        }
    }

    override suspend fun getFavorites(): List<String> {
        return favorites
    }
}