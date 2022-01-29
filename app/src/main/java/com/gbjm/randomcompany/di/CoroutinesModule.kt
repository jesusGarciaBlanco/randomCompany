package com.gbjm.randomcompany.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.internal.platform.android.AndroidSocketAdapter.Companion.factory
import javax.inject.Singleton

@Module
class CoroutinesModule {
    @Singleton
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

//    @Singleton
//    @Provides
//    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
//
//    @Singleton
//    @Provides
//    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}