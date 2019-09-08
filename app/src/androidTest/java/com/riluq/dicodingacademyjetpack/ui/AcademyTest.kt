package com.riluq.dicodingacademyjetpack.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.riluq.dicodingacademyjetpack.R
import com.riluq.dicodingacademyjetpack.ui.home.HomeActivity
import org.junit.Rule
import org.junit.Test


class AcademyTest {

    @get:Rule
    var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun toDetailActivityTest() {
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        onView(withId(R.id.rv_academy)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_academy)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_title)).check(matches(withText("Menjadi Android Developer Expert")))
    }

    @Test
    fun toReaderActivityTest() {
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        onView(withId(R.id.rv_academy)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_academy)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        onView(withId(R.id.btn_start)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_start)).perform(click())

        onView(withId(R.id.frame_container)).check(matches(isDisplayed()))

        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        onView(withId(R.id.rv_module)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_module)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        onView(withId(R.id.web_view)).check(matches(isDisplayed()))
    }
}