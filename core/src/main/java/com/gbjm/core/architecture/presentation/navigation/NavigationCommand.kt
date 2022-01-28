package com.gbjm.core.architecture.presentation.navigation

import androidx.navigation.NavDirections

/**
 * NavigationCommand provide common navigation commands for different types of navigation
 *
 */
sealed class NavigationCommand {

    /**
     * Child to main navigation
     *
     * @property directions the navigation id and data
     */
    data class FromRootToChild(val directions : NavDirections) : NavigationCommand()

    /**
     * Navigation to a specific target
     *
     * @property directions the navigation id and data
     */
    data class To(val directions: NavDirections): NavigationCommand()

    /**
     * Immediate backward navigation
     */
    object Back: NavigationCommand()

    /**
     * Backward navigation to a specific target
     *
     * @property destinationId the destination ID
     */
    data class NavigateTo(val destinationId: Int): NavigationCommand()

    /**
     * Backward navigation to a specific target
     *
     * @property destinationId the destination ID
     */
    data class BackTo(val destinationId: Int): NavigationCommand()

    /**
     * Root navigation command
     */
    object ToRoot: NavigationCommand()
}
