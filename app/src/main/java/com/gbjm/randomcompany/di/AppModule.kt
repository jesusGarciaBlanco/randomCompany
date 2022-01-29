package com.gbjm.randomcompany.di

import android.content.Context
import com.gbjm.core.api.ApiService
import com.gbjm.core.domain.UsersRemoteDataSource
import com.gbjm.core.domain.UsersRemoteDataSourceImp
import com.gbjm.randomcompany.RandomUserApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * App level dependencies
 */
@Module(
    includes = [NetworkServiceModule::class]
)
open class AppModule{

    @Singleton
    @Provides
    fun providesUserRemoteDataSource(apiService: ApiService): UsersRemoteDataSource {
        return UsersRemoteDataSourceImp(apiService)
    }

    @Provides
    @Singleton
    fun provideApplication(application: RandomUserApplication): Context {
        return application.applicationContext
    }
}