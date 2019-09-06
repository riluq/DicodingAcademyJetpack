package com.riluq.dicodingacademyjetpack.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ModuleEntity
import com.riluq.dicodingacademyjetpack.data.source.remote.RemoteRepository
import com.riluq.dicodingacademyjetpack.data.source.remote.response.CourseResponse
import com.riluq.dicodingacademyjetpack.data.source.remote.response.ModuleResponse
import com.riluq.dicodingacademyjetpack.utils.FakeDataDummyTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*


class AcademyRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteRepository::class.java)
    private val academyRepository = FakeAcademyRepository(remote)

    private val courseResponses: List<CourseResponse> = FakeDataDummyTest.generateRemoteDummyCourses()!!
    private val courseId: String = courseResponses[0].id!!
    private val modulesResponse: List<ModuleResponse> = FakeDataDummyTest.generateRemoteDummyModules(courseId)
    private val moduleId: String = modulesResponse[0].moduleId!!
    private val content = FakeDataDummyTest.generateRemoteDummyContent(moduleId)

    @Test
    fun getAllCourses() {
        `when`(remote.getAllCourses()).thenReturn(courseResponses)
        val courseEntity = academyRepository.getAllCourses()
        verify(remote).getAllCourses()
        assertNotNull(courseEntity)
        assertEquals(courseResponses.size, courseEntity?.size)
    }

    @Test
    fun getAllModulesByCourse() {
        `when`(remote.getModules(courseId)).thenReturn(modulesResponse)
        val moduleEntities: List<ModuleEntity> = academyRepository.getAllModulesByCourse(courseId)!!
        verify(remote).getModules(courseId)
        assertNotNull(moduleEntities)
        assertEquals(modulesResponse.size, moduleEntities.size)
    }

    @Test
    fun getBookmarkedCourses() {
        `when`(remote.getAllCourses()).thenReturn(courseResponses)
        val courseEntities: List<CourseEntity> = academyRepository.getBookmarkedCourses()!!
        verify(remote).getAllCourses()
        assertNotNull(courseEntities)
        assertEquals(courseResponses.size, courseEntities.size)
    }

    @Test
    fun getContent() {
        `when`(remote.getModules(courseId)).thenReturn(modulesResponse)
        `when`(remote.getContent(moduleId)).thenReturn(content)
        val resultModule = academyRepository.getContent(courseId, moduleId)
        verify(remote).getContent(moduleId)
        assertNotNull(resultModule)
        assertEquals(content.content, resultModule?.contentEntity?.content)
    }

    @Test
    fun getCourseWithModules() {
        `when`(remote.getAllCourses()).thenReturn(courseResponses)
        val resultCourse = academyRepository.getCourseWithModules(courseId)
        verify(remote).getAllCourses()
        assertNotNull(resultCourse)
        assertEquals(courseResponses[0].title, resultCourse!!.title)
    }
}