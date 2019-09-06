package com.riluq.dicodingacademyjetpack.ui.detail

import com.riluq.dicodingacademyjetpack.data.source.AcademyRepository
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.utils.FakeDataDummyTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*


class DetailCourseViewModelTest {

    private var viewModel: DetailCourseViewModel? = null
    private var dummyCourse: CourseEntity? = FakeDataDummyTest.generateDummyCourses()[0]
    private val academyRepository: AcademyRepository = mock(AcademyRepository::class.java)
    private val courseId = dummyCourse?.courseId

    @Before
    fun setUp() {
        viewModel = DetailCourseViewModel(academyRepository)
        viewModel?.setCourseId(courseId!!)
    }

    @Test
    fun getCourse() {
        `when`(academyRepository.getCourseWithModules(courseId!!)).thenReturn(dummyCourse)
        val courseEntity = viewModel!!.getCourse()
        verify(academyRepository).getCourseWithModules(courseId)
        assertNotNull(courseEntity)
        assertEquals(dummyCourse!!.courseId, courseEntity!!.courseId)
        assertEquals(dummyCourse!!.deadline, courseEntity.deadline)
        assertEquals(dummyCourse!!.description, courseEntity.description)
        assertEquals(dummyCourse!!.imagePath, courseEntity.imagePath)
        assertEquals(dummyCourse!!.title, courseEntity.title)
    }

    @Test
    fun getModules() {
        `when`(academyRepository.getAllModulesByCourse(courseId!!)).thenReturn(FakeDataDummyTest.generateDummyModules(courseId))
        val moduleEntities = viewModel!!.getModules()
        verify(academyRepository).getAllModulesByCourse(courseId)
        assertNotNull(moduleEntities)
        assertEquals(7, moduleEntities!!.size)
    }
}