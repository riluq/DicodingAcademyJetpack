package com.riluq.dicodingacademyjetpack.ui.academy

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.riluq.dicodingacademyjetpack.data.source.AcademyRepository
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.utils.FakeDataDummyTest
import com.riluq.dicodingacademyjetpack.vo.Resource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*


class AcademyViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private var viewModel: AcademyViewModel? = null
    private var academyRepository: AcademyRepository? = mock(AcademyRepository::class.java)

    val observer: Observer<Resource<List<CourseEntity>>> = mock()

    private val USERNAME = "Dicoding"

    @Before
    fun setUp() {
        viewModel = AcademyViewModel(academyRepository!!)
    }

    @Test
    fun getCourses() {
        val resource: Resource<List<CourseEntity>> = Resource.success(FakeDataDummyTest.generateDummyCourses())
        val dummyCourse= MutableLiveData<Resource<List<CourseEntity>>>()
        dummyCourse.value = resource

        `when`(academyRepository?.getAllCourses()).thenReturn(dummyCourse)

        viewModel?.setUsername(USERNAME)

        viewModel?.courses?.observeForever(observer)

        verify(observer).onChanged(resource)
    }
}