package com.riluq.dicodingacademyjetpack.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.riluq.dicodingacademyjetpack.data.source.AcademyRepository
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ModuleEntity
import com.riluq.dicodingacademyjetpack.utils.FakeDataDummyTest
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

    private val observerCourse: Observer<CourseEntity> = mock()

    private val observerModule: Observer<List<ModuleEntity>> = mock()

    @Before
    fun setUp() {
        viewModel = DetailCourseViewModel(academyRepository)
        viewModel?.setCourseId(courseId!!)
    }

    @Test
    fun getCourse() {
        val courseEntities = MutableLiveData<CourseEntity>()
        courseEntities.value = dummyCourse

        `when`(academyRepository.getCourseWithModules(courseId!!)).thenReturn(courseEntities)

        viewModel?.getCourse()?.observeForever(observerCourse)

        verify(observerCourse).onChanged(dummyCourse)

    }

    @Test
    fun getModules() {
        val moduleEntities = MutableLiveData<List<ModuleEntity>>()
        moduleEntities.value = dummyModules

        `when`(academyRepository.getAllModulesByCourse(courseId!!)).thenReturn(moduleEntities)

        viewModel?.getModules()?.observeForever(observerModule)

        verify(observerModule).onChanged(dummyModules)

    }
}