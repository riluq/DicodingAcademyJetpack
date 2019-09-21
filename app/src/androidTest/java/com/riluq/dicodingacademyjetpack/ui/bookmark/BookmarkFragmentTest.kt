package com.riluq.dicodingacademyjetpack.ui.bookmark

import androidx.test.espresso.IdlingRegistry
import androidx.test.rule.ActivityTestRule
import com.riluq.dicodingacademyjetpack.testing.SingleFragmentActivity
import com.riluq.dicodingacademyjetpack.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// Setelah proses pengujian selesai dan Anda akan membuat Proyek Academy menjadi sebuah APK,
// Anda harus menghilangkan Idling Resource agar aplikasi tidak mengalami memory leaks.
class BookmarkFragmentTest {

    @get:Rule
    val activityRule: ActivityTestRule<SingleFragmentActivity> =
        ActivityTestRule(SingleFragmentActivity::class.java)
    private var bookmarkFragment: BookmarkFragment = BookmarkFragment()

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
        activityRule.activity.setFragment(bookmarkFragment)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun loadCourses() {
//        onView(withId(R.id.rv_bookmark)).check(matches(isDisplayed()))
//        onView(withId(R.id.rv_bookmark)).check(RecyclerViewItemCountAssertion(5))
    }
}