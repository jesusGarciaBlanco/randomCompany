package com.gbjm.core.domain.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.gbjm.core.domain.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    @Query("SELECT * FROM users")
    fun getUsers(): PagingSource<Int, User>

    @Query("SELECT * FROM users WHERE uuid = :query LIMIT 1 ")
    fun getUserById(query:String): User

    @Query("SELECT * FROM users WHERE uuid LIKE :query ")
    fun pagingSource(query: String): PagingSource<Int, User>

    @Query("DELETE FROM users WHERE users.uuid LIKE :query")
    fun deleteById(query: String)

    @Query("DELETE FROM users")
    suspend fun clearAll()

    @Query("SELECT COUNT(uuid) from users")
    suspend fun count(): Int

}