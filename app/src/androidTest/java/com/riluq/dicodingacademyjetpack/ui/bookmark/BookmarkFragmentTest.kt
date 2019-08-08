package com.riluq.dicodingacademyjetpack.ui.bookmark

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.riluq.dicodingacademyjetpack.R
import com.riluq.dicodingacademyjetpack.testing.SingleFragmentActivity
import com.riluq.dicodingacademyjetpack.utils.RecyclerViewItemCountAssertion
import kotlinx.android.synthetic.main.fragment_bookmark.view.*
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class BookmarkFragmentTest {

    @get:Rule
    val activityRule: ActivityTestRule<SingleFragmentActivity> = ActivityTestRule(SingleFragmentActivity::class.java)
    private var bookmarkFragment: BookmarkFragment = BookmarkFragment()

    @Before
    fun setUp() {
        activityRule.activity.setFragment(bookmarkFragment)
    }

    @Test
    fun loadCourses() {
        onView(withId(R.id.rv_bookmark)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_bookmark)).check(RecyclerViewItemCountAssertion(5))
    }
}