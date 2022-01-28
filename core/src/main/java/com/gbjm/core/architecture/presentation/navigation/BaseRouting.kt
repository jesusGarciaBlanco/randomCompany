package com.gbjm.core.architecture.presentation.navigation

import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.gbjm.core.architecture.domain.result.Event

/**
 * BaseRouting provides a common framework for routing navigation classes
 *
 */
open class BaseRouting {

    /**
     * The minimum elapsed time allowed between navigations to the same
     * action or destination
     */
    private val MIN_TIME_BETWEEN_NAVIGATIONS_MS = 500

    /**
     * Identifier of the last action to which the routing navigated
     */
    private var lastActionId: Int? = null

    /**
     * The timestamp of the last action to which the routing navigated
     */
    private var lastActionTimestamp: Long? = null

    /**
     * Identifier of the last destination to which the routing navigated
     */
    private var lastDestinationId: Int? = null

    /**
     * The timestamp of the last destination to which the routing navigated
     */
    private var lastDestinationTimestamp: Long? = null

    /**
     * The navigation commands
     */
    var navigationCommands = MutableLiveData<Event<NavigationCommand>>()

    /**
     * The current root destination id to return in toRoot navigation command
     */
    var rootDestinationId: Int? = null

    /**
     * Provide navigation command from a root screen to a child (as a modal) screen
     *
     * @param directions the navigation command indication and data
     */
    fun navigateFromRootToChild(directions: NavDirections) {
        if (canNavigateToAction(directions.actionId)) {
            navigationCommands.value = Event(NavigationCommand.FromRootToChild(directions))
        }
    }

    /**
     * Provide navigation command from a screen to another screen
     *
     * @param directions the navigation command indication and data
     */
    fun navigate(directions: NavDirections) {
        if (canNavigateToAction(directions.actionId)) {
            navigationCommands.value = Event(NavigationCommand.To(directions))
        }
    }

    /**
     * Handle immediate backward navigation
     */
    fun back() {
        navigationCommands.value = Event(NavigationCommand.Back)
    }

    /**
     * Handle navigation to root screen
     */
    fun navigateToRoot() {
        navigationCommands.value = Event(NavigationCommand.ToRoot)
    }


    fun navigateTo(destinationId: Int) {
        if (canNavigateToDestination(destinationId)) {
            navigationCommands.value = Event(NavigationCommand.NavigateTo(destinationId))
        }
    }

    /**
     * Handle backward navigation to a specific target
     */
    fun backTo(destinationId: Int) {
        navigationCommands.value = Event(NavigationCommand.BackTo(destinationId))
    }

    /**
     * Find if the routing can navigate to an action. This is called to prevent
     * multiple navigations to the same actionId in less than a short time
     *
     * @param actionId the actionId to navigate
     * @return true if the routing can navigate to that actionId
     */
    private fun canNavigateToAction(actionId: Int): Boolean {
        val now = System.currentTimeMillis()
        val result: Boolean

        if (actionId == this.lastActionId) {

            val last = lastActionTimestamp ?: 0
            val delta = now - last
            result = delta > MIN_TIME_BETWEEN_NAVIGATIONS_MS

        } else {

            result = true
        }

        lastActionTimestamp = now
        lastActionId = actionId
        return result
    }

    /**
     * Find if the routing can navigate to a destination. This is called to prevent
     * multiple navigations to the same destinationId in less than a short time
     *
     * @param destinationId the destination id to navigate
     * @return true if the routing can navigate to that destination
     */
    private fun canNavigateToDestination(destinationId: Int): Boolean {
        val now = System.currentTimeMillis()
        val result: Boolean

        if (destinationId == this.lastDestinationId) {

            val last = lastDestinationTimestamp ?: 0
            val delta = now - last
            result = delta > MIN_TIME_BETWEEN_NAVIGATIONS_MS

        } else {

            result = true
        }

        lastDestinationTimestamp = now
        lastDestinationId = destinationId
        return result


    }


}