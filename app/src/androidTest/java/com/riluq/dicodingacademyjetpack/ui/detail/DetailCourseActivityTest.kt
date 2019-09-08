package com.riluq.dicodingacademyjetpack.ui.detail

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.riluq.dicodingacademyjetpack.R
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.utils.FakeDataDummyAndroidTest
import com.riluq.dicodingacademyjetpack.utils.RecyclerViewItemCountAssertion
import com.riluq.dicodingacademyjetpack.utils.generateDummyCourses
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class DetailCourseActivityTest {

    private val dummyCourse: CourseEntity = FakeDataDummyAndroidTest.generateDummyCourses()[0]

    @get:Rule
    var activityRule: ActivityTestRule<DetailCourseActivity> =
        object : ActivityTestRule<DetailCourseActivity>(DetailCourseActivity::class.java) {
            override fun getActivityIntent(): Intent {
                val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
                val result = Intent(targetContext, DetailCourseActivity::class.java)
                result.putExtra(DetailCourseActivity.EXTRA_COURSE, dummyCourse.courseId)
                return result
            }
        }

    @Before
    fun setUp() {
    }

    @Test
    fun loadCourse() {
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_title)).check(matches(withText(dummyCourse.title)))
        onView(withId(R.id.text_date)).check(matches(isDisplayed()))
        onView(withId(R.id.text_date)).check(matches(withText(String.format("Deadline %s", dummyCourse.deadline))))
    }

    @Test
    fun loadModules() {
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        onView(withId(R.id.rv_module)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_module)).check(RecyclerViewItemCountAssertion(7))
    }
}