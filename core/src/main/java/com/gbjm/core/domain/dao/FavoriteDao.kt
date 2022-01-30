package com.gbjm.core.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gbjm.core.domain.entity.Favorite

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(userFavorite: Favorite)

    @Query("SELECT * FROM favorites")
    fun getFavorites(): List<Favorite>

    @Query("DELETE FROM favorites WHERE uuid LIKE :query")
    fun deleteById(query: String)

    @Query("DELETE FROM favorites")
    suspend fun clearAll()
}