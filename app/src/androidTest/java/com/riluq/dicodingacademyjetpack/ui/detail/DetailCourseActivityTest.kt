package com.riluq.dicodingacademyjetpack.ui.detail

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.riluq.dicodingacademyjetpack.R
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.utils.EspressoIdlingResource
import com.riluq.dicodingacademyjetpack.utils.FakeDataDummyAndroidTest
import com.riluq.dicodingacademyjetpack.utils.RecyclerViewItemCountAssertion
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// Setelah proses pengujian selesai dan Anda akan membuat Proyek Academy menjadi sebuah APK,
// Anda harus menghilangkan Idling Resource agar aplikasi tidak mengalami memory leaks.
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
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }


    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun loadCourse() {
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_title)).check(matches(withText(dummyCourse.title)))
        onView(withId(R.id.text_date)).check(matches(isDisplayed()))
        onView(withId(R.id.text_date)).check(
            matches(
                withText(
                    String.format(
                        "Deadline %s",
                        dummyCourse.deadline
                    )
                )
            )
        )
    }

    @Test
    fun loadModules() {
        onView(withId(R.id.rv_module)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_module)).check(RecyclerViewItemCountAssertion(7))
    }
}