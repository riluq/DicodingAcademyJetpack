package com.riluq.dicodingacademyjetpack.ui.academy

import com.riluq.dicodingacademyjetpack.data.source.AcademyRepository
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.utils.FakeDataDummyTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class AcademyViewModelTest {

    private var viewModel: AcademyViewModel? = null
    private var academyRepository: AcademyRepository? = mock(AcademyRepository::class.java)

    @Before
    fun setUp() {
        viewModel = AcademyViewModel(academyRepository!!)
    }

    @Test
    fun getCourses() {
        `when`(academyRepository?.getAllCourses()).thenReturn(FakeDataDummyTest.generateDummyCourses())
        val courseEntity: List<CourseEntity>? = viewModel?.getCourses()
        verify(academyRepository)?.getAllCourses()
        assertNotNull(courseEntity)
        assertEquals(5, courseEntity?.size)
    }
}