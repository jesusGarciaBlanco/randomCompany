package com.gbjm.randomcompany.di.detail

import androidx.lifecycle.ViewModel
import com.gbjm.randomcompany.di.ViewModelKey
import com.gbjm.randomcompany.ui.detail.UserDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [UserDetailFragmentModule::class])
internal abstract class UserDetailModule {

    /**
    The ViewModels are created by Dagger in a map. Via the @ViewModelKey, we define that we
     * want to get a [UserDetailViewModel] class.
     */
    @Binds
    @IntoMap
    @ViewModelKey(UserDetailViewModel::class)
    abstract fun bindUserDetailViewModel(viewModel: UserDetailViewModel): ViewModel
}