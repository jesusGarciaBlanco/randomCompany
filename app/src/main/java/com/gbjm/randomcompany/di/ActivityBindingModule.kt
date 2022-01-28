package com.gbjm.randomcompany.di

import com.gbjm.core.di.annotations.ActivityScoped
import com.gbjm.core.di.annotations.FragmentScoped
import com.gbjm.randomcompany.MainActivity
import com.gbjm.randomcompany.StartFragment
import com.gbjm.randomcompany.di.detail.UserDetailModule
import com.gbjm.randomcompany.di.users.UsersModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [
        UsersModule::class,
        UserDetailModule::class])

    internal abstract fun bindMainActivity(): MainActivity

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeStartFragment(): StartFragment

}