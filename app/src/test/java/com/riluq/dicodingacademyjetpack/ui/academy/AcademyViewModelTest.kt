package com.riluq.dicodingacademyjetpack.ui.academy

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.riluq.dicodingacademyjetpack.data.source.AcademyRepository
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.utils.FakeDataDummyTest
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

    val observer: Observer<List<CourseEntity>> = mock()

    @Before
    fun setUp() {
        viewModel = AcademyViewModel(academyRepository!!)
    }

    @Test
    fun getCourses() {
        val dummyCourse: List<CourseEntity> = FakeDataDummyTest.generateDummyCourses()

        val courses = MutableLiveData<List<CourseEntity>>()
        courses.value = dummyCourse

        `when`(academyRepository?.getAllCourses()).thenReturn(courses)

        viewModel?.getCourses()?.observeForever(observer)

        verify(observer).onChanged(dummyCourse)
    }
}