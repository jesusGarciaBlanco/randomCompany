package com.gbjm.randomcompany

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not
import org.hamcrest.core.StringContains.containsString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class NavigationTest {

    @get:Rule var activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java)

    @Before fun setup() {

    }

    /**
     * navigate to the users list
     */
    @Test fun checkNavigationToUsersList(){
        Thread.sleep(2000)

        Espresso.onView(withId(R.id.initiateAppBtn))
            .check(matches(ViewMatchers.isDisplayed()))

        Espresso.onView(withId(R.id.initiateAppBtn))
            .perform(ViewActions.click())

        Thread.sleep(2000)

        //check if users recycler view is displayed
        Espresso.onView(withId(R.id.recycler)).check(matches(ViewMatchers.isDisplayed()))
    }

    /**
     * navigate to the users list
     */
    @Test fun checkNavigateToUsersDetail(){
        checkNavigationToUsersList()

        //click on item 0 of the recycler view to navigate to the first user detail
        Espresso.onView(withId(R.id.recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, object : ViewAction {
                override fun getConstraints(): Matcher<View> {
                    TODO("Not yet implemented")
                }

                override fun getDescription(): String {
                    return "Click on specific button"
                }

                override fun perform(uiController: UiController, view: View) {
                    val button = view.findViewById<View>(R.id.ivUser) // Maybe check for null
                    button.performClick()
                }
            }))

        Thread.sleep(2000)

        //check if image view user detail is displayed
        Espresso.onView(withId(R.id.ivUserDetail)).check(
            matches(ViewMatchers.isDisplayed()))

        //check if textview user name detail is displayed
        Espresso.onView(withId(R.id.tvNameDetail)).check(
            matches(ViewMatchers.isDisplayed()))
        //check if textview user description detail is displayed
        Espresso.onView(withId(R.id.tvSurnameDetail)).check(
            matches(ViewMatchers.isDisplayed()))
    }

    @Test fun checkUserListNotNavigateClickingOnParent() {
        checkNavigationToUsersList()

        //click on item 0 of the recycler view to navigate to the first user detail
        Espresso.onView(withId(R.id.recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, object : ViewAction {
                override fun getConstraints(): Matcher<View> {
                    TODO("Not yet implemented")
                }

                override fun getDescription(): String {
                    return "Click on specific button"
                }

                override fun perform(uiController: UiController, view: View) {
//                    clicking on parent to check if we cant navigate pressing parent item row
                    val button = view.findViewById<View>(R.id.parent) // Maybe check for null
                    button.performClick()
                }
            }))

        Thread.sleep(2000)

        //check if image view user detail is displayed
        Espresso.onView(withId(R.id.ivUserDetail)).check(doesNotExist())

        //check if textview user name detail is displayed
        Espresso.onView(withId(R.id.ivUserDetail)).check(doesNotExist())
        //check if textview user surname detail is displayed
        Espresso.onView(withId(R.id.tvSurnameDetail)).check(doesNotExist())

    }

    @Test fun checkUserDetailValuesItemOne() {
        checkNavigationToUsersList()

        //click on item 1 of the recycler view to navigate to user detail
        Espresso.onView(withId(R.id.recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, object : ViewAction {
                override fun getConstraints(): Matcher<View> {
                    TODO("Not yet implemented")
                }

                override fun getDescription(): String {
                    return "Click on specific button"
                }

                override fun perform(uiController: UiController, view: View) {
                    val button = view.findViewById<View>(R.id.ivUser) // Maybe check for null
                    button.performClick()
                }
            }))

        Thread.sleep(2000)

        //check if image view user detail is displayed
        Espresso.onView(withId(R.id.ivUserDetail)).check(
            matches(ViewMatchers.isDisplayed()))

        //check if textview user name detail is displayed
        Espresso.onView(withId(R.id.tvNameDetail)).check(
            matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvNameDetail)).check(matches(withText("Mr Pjotr")));
        //check if textview user surname detail is displayed
        Espresso.onView(withId(R.id.tvSurnameDetail)).check(
            matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvSurnameDetail)).check(matches(withText(containsString("Van Dijk"))))

    }
}