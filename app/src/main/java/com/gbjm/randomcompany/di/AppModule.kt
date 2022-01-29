package com.gbjm.randomcompany.di

import android.content.Context
import com.gbjm.randomcompany.RandomUserApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * App level dependencies
 */
@Module(
    includes = [NetworkServiceModule::class, DatabaseModule::class]
)
open class AppModule{

    @Provides
    @Singleton
    fun provideApplication(application: RandomUserApplication): Context {
        return application.applicationContext
    }
}