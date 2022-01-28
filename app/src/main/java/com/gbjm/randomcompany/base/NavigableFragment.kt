package com.gbjm.randomcompany.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.gbjm.core.architecture.domain.result.EventObserver
import com.gbjm.core.architecture.presentation.navigation.BaseRouting
import com.gbjm.core.architecture.presentation.navigation.NavigationCommand
import com.gbjm.randomcompany.R

/**
 * NavigableFragment is the base implementation for fragments using the navigation graph
 */
abstract class NavigableFragment<VM : ViewModel, DB : ViewDataBinding> :
    BaseFragment<VM, DB>() {

    /**
     * The main navigation controller
     */
    private var mainNavController: NavController? = null

    /**
     * The related routing instance
     *
     * @return the routing instance
     */
    abstract fun getRouting(): BaseRouting

    /**
     * Listens to navigation commands
     *
     * @param savedInstanceState the persisted state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getRouting().navigationCommands.observe(this, EventObserver { command ->
            when (command) {
                is NavigationCommand.FromRootToChild -> {
                    val navController = mainNavController
                    if (navController != null) {
                        navigateTo(navController, command.directions)
                    }
                }
                is NavigationCommand.To -> {
                    navigateTo(findNavController(), command.directions)
                }
                is NavigationCommand.Back -> findNavController().popBackStack()
                is NavigationCommand.BackTo -> findNavController().popBackStack(command.destinationId, false)
                is NavigationCommand.NavigateTo -> {
                    findNavController().navigate(command.destinationId)
                }
            }
        })
    }

    /**
     * Obtains main navigation controller
     *
     * @param view the created view
     * @param savedInstanceState the persisted state
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { activity ->
            mainNavController = Navigation.findNavController(activity, R.id.mainHostFragment)
        }
    }

    /**
     * Navigate to a destination
     *
     * @param navController the navigation controller used
     * @param directions the destination target and animations
     */
    private fun navigateTo(navController: NavController, directions: NavDirections) {
        val currentDestination = navController.currentDestination
        val action = currentDestination?.getAction(directions.actionId)
        if (action != null) {
            navController.navigate(directions)
        }
    }

    /**
     * Goes back to previous navigation fragment
     */
    protected fun goBack() {
        activity?.onBackPressed()
    }

}