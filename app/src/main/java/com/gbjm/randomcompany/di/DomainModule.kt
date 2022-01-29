package com.gbjm.randomcompany.di

import com.gbjm.core.api.ApiService
import com.gbjm.core.domain.AppDatabase
import com.gbjm.core.domain.UsersRepository
import com.gbjm.core.domain.UsersRepositoryImp
import com.gbjm.core.domain.mapper.UserMapperImp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module (
    includes = [CoroutinesModule::class]
)
class DomainModule {

    @Singleton
    @Provides
    fun providesUserListRepository(api: ApiService, db: AppDatabase, mapper: UserMapperImp): UsersRepository {
        val repository = UsersRepositoryImp(api, db, mapper)
        return repository
    }


}