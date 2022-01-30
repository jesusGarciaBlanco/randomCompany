package com.gbjm.randomcompany

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class DeleteFeatureTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        }

    /**
     * navigate to the users list
     */
    fun checkNavigationToUsersList(){
        Thread.sleep(2000)

        onView(withId(R.id.initiateAppBtn))
            .check(matches(isDisplayed()))

        onView(withId(R.id.initiateAppBtn))
            .perform(click())
    }

    /**
     * check user delete
     */
    @Test
    fun checkDeleteUser(){
        checkNavigationToUsersList()

        onView(withId(R.id.swipeRefreshLayout))
            .perform(withCustomConstraints(swipeDown(), isDisplayingAtLeast(85)));

        Thread.sleep(2000)

        //check if user that we are going to delete is in the list at this moment
        onView(withId(R.id.recycler)).check(matches(hasItem(hasDescendant(withText("Mrs Rosalyn")))))
        //click on item 0 deleteButton of the recycler view to delete user
        onView(withId(R.id.recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, object :
                ViewAction {
                override fun getConstraints(): Matcher<View> {
                    TODO("Not yet implemented")
                }

                override fun getDescription(): String {
                    return "Click on specific button"
                }

                override fun perform(uiController: UiController, view: View) {
                    val button = view.findViewById<View>(R.id.btnDelete) // Maybe check for null
                    button.performClick()
                }
            }))

        onView(withText("YES"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
            .perform(click())

        Thread.sleep(2000)

        //check if item 0 waa deleted successfully
        onView(withId(R.id.recycler)).check(matches(not(hasItem(hasDescendant(withText("Mrs Rosalyn"))))))

        onView(withId(R.id.swipeRefreshLayout))
            .perform(withCustomConstraints(swipeDown(), isDisplayingAtLeast(85)));

    }

    fun hasItem(matcher: Matcher<View?>): Matcher<View?>? {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has item: ")
                matcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val adapter = view.adapter
                for (position in 0 until adapter!!.itemCount) {
                    val type = adapter.getItemViewType(position)
                    val holder = adapter.createViewHolder(view, type)
                    adapter.onBindViewHolder(holder, position)
                    if (matcher.matches(holder.itemView)) {
                        return true
                    }
                }
                return false
            }
        }
    }

    fun withCustomConstraints(action: ViewAction, constraints: Matcher<View>): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return constraints
            }

            override fun getDescription(): String {
                return action.description
            }

            override fun perform(uiController: UiController, view: View) {
                action.perform(uiController, view)
            }
        }
    }
}