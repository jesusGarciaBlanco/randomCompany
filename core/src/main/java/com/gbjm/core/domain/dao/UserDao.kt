package com.gbjm.core.domain.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gbjm.core.domain.entity.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    @Query("SELECT * FROM users")
    fun getUsers(): PagingSource<Int, User>

    @Query("SELECT * FROM users WHERE id LIKE :query")
    fun pagingSource(query: String): PagingSource<Int, User>

    @Query("DELETE FROM users WHERE id LIKE :query")
    fun deleteById(query: String)

    @Query("DELETE FROM users")
    suspend fun clearAll()

    @Query("SELECT COUNT(id) from users")
    suspend fun count(): Int

}