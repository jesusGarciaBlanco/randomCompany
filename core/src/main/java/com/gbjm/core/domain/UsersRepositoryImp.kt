package com.gbjm.core.domain

import androidx.paging.*
import com.gbjm.core.api.ApiService
import com.gbjm.core.domain.entity.Favorite
import com.gbjm.core.domain.entity.User
import com.gbjm.core.domain.mapper.UserMapperImp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

const val NETWORK_PAGE_SIZE = 10

class UsersRepositoryImp @Inject constructor(
    private val usersApi: ApiService,
    private val db: AppDatabase,
    private val userMapper: UserMapperImp
) : UsersRepository {

    private val pagingSourceFactory = { db.userDao.getUsers() }

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

    override suspend fun deleteUser(id: String) {
        db.userDao.deleteById(id)
    }

    override suspend fun clearUsers() {
        db.userDao.clearAll()
    }

    override fun getUserDetail(id: String): User {
        return db.userDao.getUserById(id)
    }

    override suspend fun getFavorites(): List<Favorite>{
        return db.favoriteDao.getFavorites()
    }

    override suspend fun addFavorite(id: String) {
        db.favoriteDao.insertFavorite(Favorite(id))
    }

    override suspend fun deleteFavorite(id: String) {
        db.favoriteDao.deleteById(id)
    }
}