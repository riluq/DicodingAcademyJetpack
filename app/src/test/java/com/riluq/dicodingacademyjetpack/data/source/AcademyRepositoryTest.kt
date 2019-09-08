package com.riluq.dicodingacademyjetpack.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ModuleEntity
import com.riluq.dicodingacademyjetpack.data.source.remote.RemoteRepository
import com.riluq.dicodingacademyjetpack.data.source.remote.response.CourseResponse
import com.riluq.dicodingacademyjetpack.data.source.remote.response.ModuleResponse
import com.riluq.dicodingacademyjetpack.utils.FakeDataDummyTest
import com.riluq.dicodingacademyjetpack.utils.LiveDataTestUtil
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test

class AcademyRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote: RemoteRepository = mock()
    private val academyRepository = FakeAcademyRepository(remote)

    private val courseResponses: List<CourseResponse> =
        FakeDataDummyTest.generateRemoteDummyCourses()!!
    private val courseId: String = courseResponses[0].id!!
    private val modulesResponse: List<ModuleResponse> =
        FakeDataDummyTest.generateRemoteDummyModules(courseId)
    private val moduleId: String = modulesResponse[0].moduleId!!
    private val content = FakeDataDummyTest.generateRemoteDummyContent(moduleId)

    @Test
    fun getAllCourses() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteRepository.LoadCourseCallback)
                .onAllCoursesReceived(courseResponses)
            null
        }.`when`(remote).getAllCourses(any())

        val result: List<CourseEntity> =
            LiveDataTestUtil.getValue(academyRepository.getAllCourses()!!)

        verify(remote, times(1)).getAllCourses(any())

        assertNotNull(result)
        assertEquals(courseResponses.size, result.size)
    }

    @Test
    fun getAllModulesByCourse() {

        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteRepository.LoadModuleCallback)
                .onAllModulesReceived(modulesResponse)
            null
        }.`when`(remote).getModules(
            eq(courseId),
            any()
        )

        val result: List<ModuleEntity> =
            LiveDataTestUtil.getValue(academyRepository.getAllModulesByCourse(courseId)!!)

        verify(
            remote,
            times(1)
        ).getModules(eq(courseId), any())

        assertNotNull(result)
        assertEquals(modulesResponse.size, result.size)
    }

    @Test
    fun getBookmarkedCourses() {

        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteRepository.LoadCourseCallback)
                .onAllCoursesReceived(courseResponses)
            null
        }.`when`(remote).getAllCourses(any())

        val result: List<CourseEntity> =
            LiveDataTestUtil.getValue(academyRepository.getBookmarkedCourses()!!)

        verify(remote, times(1)).getAllCourses(any())

        assertNotNull(result)
        assertEquals(courseResponses.size, result.size)
    }

    @Test
    fun getContent() {

        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteRepository.LoadModuleCallback)
                .onAllModulesReceived(modulesResponse)
            null
        }.`when`(remote).getModules(
            eq(courseId),
            any()
        )

        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteRepository.GetContentCallback)
                .onContentReceived(content)
            null
        }.`when`(remote).getContent(
            eq(moduleId),
            any()
        )

        val resultContent: ModuleEntity =
            LiveDataTestUtil.getValue(academyRepository.getContent(courseId, moduleId)!!)

        verify(
            remote,
            times(1)
        ).getModules(eq(courseId), any())

        verify(
            remote,
            times(1)
        ).getContent(eq(moduleId), any())

        assertNotNull(resultContent)
        assertNotNull(resultContent.contentEntity)
        assertNotNull(resultContent.contentEntity?.content)
        assertEquals(content.content, resultContent.contentEntity?.content)
    }

    @Test
    fun getCourseWithModules() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteRepository.LoadCourseCallback)
                .onAllCoursesReceived(courseResponses)
            null
        }.`when`(remote).getAllCourses(any())

        val result: CourseEntity =
            LiveDataTestUtil.getValue(academyRepository.getCourseWithModules(courseId)!!)

        verify(remote, times(1)).getAllCourses(any())

        assertNotNull(result)
        assertNotNull(result.title)
        assertEquals(courseResponses[0].title, result.title)
    }
}