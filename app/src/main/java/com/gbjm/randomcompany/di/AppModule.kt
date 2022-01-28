package com.gbjm.randomcompany.di

import android.content.Context
import com.gbjm.randomcompany.RandomUserApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * App level dependencies
 */
@Module
open class AppModule{

    @Provides
    @Singleton
    fun provideApplication(application: RandomUserApplication): Context {
        return application.applicationContext
    }
}