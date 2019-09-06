package com.riluq.dicodingacademyjetpack.ui.reader

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.riluq.dicodingacademyjetpack.R
import com.riluq.dicodingacademyjetpack.utils.FakeDataDummyAndroidTest
import com.riluq.dicodingacademyjetpack.utils.RecyclerViewItemCountAssertion
import com.riluq.dicodingacademyjetpack.utils.generateDummyCourses
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class CourseReaderActivityTest {

    private val dummyCourse = FakeDataDummyAndroidTest.generateDummyCourses()[0]

    @get:Rule
    var activityRule: ActivityTestRule<CourseReaderActivity> =
        object : ActivityTestRule<CourseReaderActivity>(CourseReaderActivity::class.java) {
            override fun getActivityIntent(): Intent {
                val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
                val result = Intent(targetContext, CourseReaderActivity::class.java)
                result.putExtra(CourseReaderActivity.EXTRA_COURSE_ID, dummyCourse.courseId)
                return result
            }
        }

    @Before
    fun setUp() {
    }

    @Test
    fun loadModules() {
        onView(withId(R.id.rv_module)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_module)).check(RecyclerViewItemCountAssertion(7))
    }

    @Test
    fun clickModule() {
        onView(withId(R.id.rv_module)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_module)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.web_view)).check(matches(isDisplayed()))
    }
}