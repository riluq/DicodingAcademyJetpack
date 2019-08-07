package com.riluq.dicodingacademyjetpack.ui.academy

import com.riluq.dicodingacademyjetpack.data.CourseEntity
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class AcademyViewModelTest {

    private var viewModel: AcademyViewModel? = null

    @Before
    fun setUp() {
        viewModel = AcademyViewModel()
    }

    @Test
    fun getCourses() {
        val courseEntity: MutableList<CourseEntity>? = viewModel?.getCourses()
        assertNotNull(courseEntity)
        assertEquals(5, courseEntity?.size)
    }
}