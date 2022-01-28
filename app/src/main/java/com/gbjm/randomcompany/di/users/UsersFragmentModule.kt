package com.gbjm.randomcompany.di.users

import com.gbjm.core.di.annotations.FragmentScoped
import com.gbjm.randomcompany.ui.users.UsersListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class UsersFragmentModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeUsersListFragment(): UsersListFragment

}
