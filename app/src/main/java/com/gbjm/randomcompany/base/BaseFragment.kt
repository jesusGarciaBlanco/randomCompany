package com.gbjm.randomcompany.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import dagger.android.support.DaggerFragment

/**
 * BaseFragment is the base implementation for all fragments providing support for the inner
 * view model and data binding
 *
 * @param VM the view model class
 * @param DB the data binding class
 *
 */
abstract class BaseFragment<VM: ViewModel, DB : ViewDataBinding> : DaggerFragment() {

    private var realBinding: DB? = null

    /**
     * The data binding
     */
    protected val binding: DB
        get() = realBinding
            ?: throw IllegalStateException("Trying to access the binding outside of the view lifecycle.")

    /**
     * Provides the layout resource
     *
     * @return the layout resource ID
     */
    @LayoutRes
    abstract fun getLayoutRes(): Int

    /**
     * Provides the view model class
     *
     * @return the view model class
     */
    abstract fun getViewModel(): VM

    /**
     * Perform the view creation and data binding at this stage
     *
     * @param inflater the view inflater
     * @param container the upper view container
     * @param savedInstanceState the bundle for fragment state restoration
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return DataBindingUtil.inflate<DB>(inflater, getLayoutRes(), container, false).also {
            realBinding = it
        }.root
    }

    /**
     * Apply insets after view creation if required
     *
     * @param view the created view
     * @param savedInstanceState the bundle for fragment state restoration
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        realBinding?.lifecycleOwner = viewLifecycleOwner
        //manually call to get access to systemWindowInsetTop (status bar inset)
        ViewCompat.requestApplyInsets(view)
    }

    open protected fun isBindingInitialized(): Boolean {
        return realBinding != null
    }

    override fun onDestroyView() {
        realBinding = null
        super.onDestroyView()
    }
}