package com.riluq.dicodingacademyjetpack.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.*
import com.riluq.dicodingacademyjetpack.data.source.local.LocalRepository
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseWithModule
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ModuleEntity
import com.riluq.dicodingacademyjetpack.data.source.remote.RemoteRepository
import com.riluq.dicodingacademyjetpack.data.source.remote.response.CourseResponse
import com.riluq.dicodingacademyjetpack.data.source.remote.response.ModuleResponse
import com.riluq.dicodingacademyjetpack.utils.*
import com.riluq.dicodingacademyjetpack.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`

class AcademyRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val local: LocalRepository = mock()
    private val remote: RemoteRepository = mock()
    private val instantAppExecutor: InstantAppExecutors = mock()
    private val academyRepository = FakeAcademyRepository(local, remote, instantAppExecutor)

    private val courseResponses: List<CourseResponse> =
        FakeDataDummyTest.generateRemoteDummyCourses()!!
    private val courseEntities = FakeDataDummyTest.generateDummyCourses()
    private val courseId: String = courseResponses[0].id!!
    private val modulesResponse: List<ModuleResponse> =
        FakeDataDummyTest.generateRemoteDummyModules(courseId)
    private val moduleId: String = modulesResponse[0].moduleId!!
    private val content = FakeDataDummyTest.generateRemoteDummyContent(moduleId)

    @Test
    fun getAllCourses() {
        val dummyCourses = MutableLiveData<List<CourseEntity>>()
        dummyCourses.value = FakeDataDummyTest.generateDummyCourses()

        `when`(local.getAllCourses()).thenReturn(dummyCourses)

        val result: Resource<List<CourseEntity>> =
            LiveDataTestUtil.getValue(academyRepository.getAllCourses()!!)

        verify(local).getAllCourses()

        assertNotNull(result.data)
        assertEquals(courseResponses.size, result.data?.size)
    }

    @Test
    fun getAllModulesByCourse() {
        val dummyModules = MutableLiveData<List<ModuleEntity>>()
        dummyModules.value = FakeDataDummyTest.generateDummyModules(courseId)

        `when`(local.getAllModulesByCourse(courseId)).thenReturn(dummyModules)

        val result: Resource<List<ModuleEntity>> =
            LiveDataTestUtil.getValue(academyRepository.getAllModulesByCourse(courseId)!!)
        verify(local).getAllModulesByCourse(courseId)

        assertNotNull(result.data)
        assertEquals(modulesResponse.size, result.data?.size)
    }

    @Test
    fun getBookmarkedCourses() {
        val dataSourceFactory: DataSource.Factory<Int, CourseEntity> = mock()

        `when`(local.getBookmarkedCoursesPaged()).thenReturn(dataSourceFactory)
        academyRepository.getBookmarkedCoursesPaged()
        val result: Resource<PagedList<CourseEntity>> = Resource.success(PagedListUtil.mockPagedList(courseEntities))

        verify(local).getBookmarkedCoursesPaged()
        assertNotNull(result.data)
        assertEquals(courseEntities.size, result.data?.size)
    }

    @Test
    fun getContent() {

        val dummyEntity = MutableLiveData<ModuleEntity>()
        dummyEntity.value = FakeDataDummyTest.generateDummyModuleWithContent(courseId)

        `when`(local.getModuleWithContent(courseId)).thenReturn(dummyEntity)

        val result: Resource<ModuleEntity> = LiveDataTestUtil.getValue(academyRepository.getContent(courseId)!!)

        verify(local).getModuleWithContent(courseId)
        assertNotNull(result)

        assertNotNull(result.data)
        assertNotNull(result.data?.contentEntity)
        assertNotNull(result.data?.contentEntity?.content)
        assertEquals(content.content, result.data?.contentEntity?.content)
    }

    @Test
    fun getCourseWithModules() {
        val dummyEntity = MutableLiveData<CourseWithModule>()
        dummyEntity.value =
            FakeDataDummyTest.generateDummyCourseWithModules(FakeDataDummyTest.generateDummyCourses()[0],
                false)

        `when`(local.getCourseWithModules(courseId)).thenReturn(dummyEntity)

        val result: Resource<CourseWithModule> =
            LiveDataTestUtil.getValue(academyRepository.getCourseWithModules(courseId)!!)

        verify(local).getCourseWithModules(courseId)

        assertNotNull(result.data)
        assertNotNull(result.data?.course?.title)
        assertEquals(courseResponses[0].title, result.data?.course?.title)
    }
}