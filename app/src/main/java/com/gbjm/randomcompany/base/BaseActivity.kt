package com.gbjm.randomcompany.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import dagger.android.DaggerActivity

/**
 * BaseActivity is the base implementation for all activities providing support for the inner
 * view model and data binding
 *
 * @param VM the view model class
 * @param DB the data binding class
 *
 */
abstract class BaseActivity<VM : ViewModel, DB : ViewDataBinding> : DaggerActivity() {

    /**
     * The data binding
     */
    open lateinit var binding: DB

    /**
     * Provides the layout resource
     *
     * @return the layout resource ID
     */
    @LayoutRes abstract fun getLayoutRes(): Int

    /**
     * Provides the view model class
     *
     * @return the view model class
     */
    abstract fun getViewModel(): VM

    /**
     * Perform data binding at this stage
     *
     * @param savedInstanceState the bundle for activity state storage
     */
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    /**
     * Ensures data binding availability
     */
    private fun performDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutRes())
        binding.executePendingBindings()
    }
}