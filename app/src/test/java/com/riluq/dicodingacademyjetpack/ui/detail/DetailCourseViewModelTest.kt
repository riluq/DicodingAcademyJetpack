package com.riluq.dicodingacademyjetpack.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.riluq.dicodingacademyjetpack.data.source.AcademyRepository
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseWithModule
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ModuleEntity
import com.riluq.dicodingacademyjetpack.utils.FakeDataDummyTest
import com.riluq.dicodingacademyjetpack.vo.Resource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*


class DetailCourseViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private var viewModel: DetailCourseViewModel? = null
    private var dummyCourse: CourseEntity? = FakeDataDummyTest.generateDummyCourses()[0]
    private val academyRepository: AcademyRepository = mock(AcademyRepository::class.java)
    private val courseId = dummyCourse?.courseId
    private val dummyModules: List<ModuleEntity> = FakeDataDummyTest.generateDummyModules(courseId!!)

    private val observer: Observer<Resource<CourseWithModule>> = mock()

    @Before
    fun setUp() {
        viewModel = DetailCourseViewModel(academyRepository)
        viewModel?.setCourseId(courseId!!)
    }

    @Test
    fun getCourseWithModule() {
        val resource: Resource<CourseWithModule> =
            Resource.success(FakeDataDummyTest.generateDummyCourseWithModules(dummyCourse!!, true))
        val courseEntities = MutableLiveData<Resource<CourseWithModule>>()
        courseEntities.value = resource

        `when`(academyRepository.getCourseWithModules(courseId!!)).thenReturn(courseEntities)

        viewModel?.courseModule?.observeForever(observer)

        verify(observer).onChanged(resource)
    }

}