package com.riluq.dicodingacademyjetpack.ui.reader

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.riluq.dicodingacademyjetpack.data.source.AcademyRepository
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ContentEntity
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ModuleEntity
import com.riluq.dicodingacademyjetpack.utils.FakeDataDummyTest
import com.riluq.dicodingacademyjetpack.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*


class CourseReaderViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private var viewModel: CourseReaderViewModel? = null
    private var academyRepository = mock(AcademyRepository::class.java)
    private var dummyCourse: CourseEntity? = FakeDataDummyTest.generateDummyCourses()[0]
    private var courseId = dummyCourse?.courseId
    private var dummyModules: List<ModuleEntity> = FakeDataDummyTest.generateDummyModules(courseId!!)
    private var moduleId = dummyModules[0].moduleId

    val observerListModule: Observer<Resource<List<ModuleEntity>>> = mock()

    val observerModule: Observer<Resource<ModuleEntity>> = mock()

    @Before
    fun setUp() {
        viewModel = CourseReaderViewModel(academyRepository)
        viewModel!!.setCourseId(courseId!!)
    }

    @Test
    fun getModules() {
        val resource: Resource<List<ModuleEntity>> = Resource.success(dummyModules)
        val moduleEntities = MutableLiveData<Resource<List<ModuleEntity>>>()
        moduleEntities.value = resource

        `when`(academyRepository.getAllModulesByCourse(courseId!!)).thenReturn(moduleEntities)

        viewModel?.modules?.observeForever(observerListModule)

        verify(observerListModule).onChanged(resource)
    }

    @Test
    fun getSelectedModule() {
        val moduleEntity = MutableLiveData<Resource<ModuleEntity>>()

        val dummyModule = dummyModules[0]
        val content =
            "<h3 class=\"fr-text-bordered\">Modul 0 : Introduction</h3><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>"
        dummyModule.contentEntity = ContentEntity(content)
        val resource: Resource<ModuleEntity> = Resource.success(dummyModule)
        moduleEntity.value = resource

        `when`(academyRepository.getContent(moduleId)).thenReturn(moduleEntity)

        viewModel?.setSelectedModule(moduleId)

        viewModel?.selectedModule?.observeForever(observerModule)

        verify(observerModule).onChanged(resource)

    }
}