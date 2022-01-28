package com.gbjm.randomcompany.di.detail

import com.gbjm.core.di.annotations.FragmentScoped
import com.gbjm.randomcompany.ui.detail.UserDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class UserDetailFragmentModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeUserDetailFragment(): UserDetailFragment

}