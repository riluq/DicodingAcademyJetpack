package com.riluq.dicodingacademyjetpack.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import org.junit.Assert.assertNotNull
import org.hamcrest.core.Is.`is`

class RecyclerViewItemCountAssertion(expectedCount: Int): ViewAssertion {

    private var expectedCount: Int? = null

    init {
        this.expectedCount = expectedCount
    }

    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        val recyclerView: RecyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        assertNotNull(adapter)
        assertThat(adapter?.itemCount, `is`(expectedCount))
    }
}