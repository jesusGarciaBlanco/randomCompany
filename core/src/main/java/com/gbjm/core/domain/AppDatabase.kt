package com.gbjm.core.domain

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gbjm.core.domain.dao.FavoriteDao
import com.gbjm.core.domain.dao.UserDao
import com.gbjm.core.domain.entity.Favorite
import com.gbjm.core.domain.entity.User

/**
 * The Room database for this app
 */
@Database(entities = [User::class, Favorite::class], version = 4, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val favoriteDao: FavoriteDao

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "randomCompany-db"
            ).fallbackToDestructiveMigration()
                .build()
    }
}