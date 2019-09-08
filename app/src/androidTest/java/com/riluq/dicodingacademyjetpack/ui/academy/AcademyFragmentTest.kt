package com.riluq.dicodingacademyjetpack.ui.academy

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.riluq.dicodingacademyjetpack.R
import com.riluq.dicodingacademyjetpack.testing.SingleFragmentActivity
import com.riluq.dicodingacademyjetpack.utils.RecyclerViewItemCountAssertion
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class AcademyFragmentTest {

    @get:Rule
    val activityRule: ActivityTestRule<SingleFragmentActivity> = ActivityTestRule(SingleFragmentActivity::class.java)
    private val academyFragment: AcademyFragment = AcademyFragment()

    @Before
    fun setUp() {
        activityRule.activity.setFragment(academyFragment)
    }

    @Test
    fun loadCourses() {
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        onView(withId(R.id.rv_academy)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_academy)).check(RecyclerViewItemCountAssertion(5))
    }
}