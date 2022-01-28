package com.gbjm.randomcompany.di.users

import androidx.lifecycle.ViewModel
import com.gbjm.randomcompany.di.ViewModelKey
import com.gbjm.randomcompany.ui.users.UsersListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [UsersFragmentModule::class])
internal abstract class UsersModule {

    /**
    The ViewModels are created by Dagger in a map. Via the @ViewModelKey, we define that we
     * want to get a [UsersListViewModel] class.
     */
    @Binds
    @IntoMap
    @ViewModelKey(UsersListViewModel::class)
    abstract fun bindUsersListViewModel(viewModel: UsersListViewModel): ViewModel
}
