package com.riluq.dicodingacademyjetpack.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.riluq.dicodingacademyjetpack.R
import com.riluq.dicodingacademyjetpack.ui.home.HomeActivity
import com.riluq.dicodingacademyjetpack.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// Setelah proses pengujian selesai dan Anda akan membuat Proyek Academy menjadi sebuah APK,
// Anda harus menghilangkan Idling Resource agar aplikasi tidak mengalami memory leaks.
class AcademyTest {

    @get:Rule
    var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }


    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun toDetailActivityTest() {
        onView(withId(R.id.rv_academy)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_academy)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_title)).check(matches(withText("Menjadi Android Developer Expert")))
    }

    @Test
    fun toReaderActivityTest() {
        onView(withId(R.id.rv_academy)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_academy)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.btn_start)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_start)).perform(click())

        try {
            onView(withId(R.id.frame_container)).check(matches(isDisplayed()))

            onView(withId(R.id.rv_module)).check(matches(isDisplayed()))
            onView(withId(R.id.rv_module)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )

            onView(withId(R.id.web_view)).check(matches(isDisplayed()))
        } catch (e: NoMatchingViewException) {
            onView(withId(R.id.frame_list)).check(matches(isDisplayed()))

            onView(withId(R.id.rv_module)).check(matches(isDisplayed()))
            onView(withId(R.id.rv_module)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )

            onView(withId(R.id.web_view)).check(matches(isDisplayed()))
            onView(withId(R.id.web_view)).check(matches(isDisplayed()))
        }
    }
}