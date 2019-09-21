package com.riluq.dicodingacademyjetpack.ui.bookmark

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.riluq.dicodingacademyjetpack.data.source.AcademyRepository
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.utils.FakeDataDummyTest
import com.riluq.dicodingacademyjetpack.vo.Resource
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito.*

class BookmarkViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private var viewModel: BookmarkViewModel? = null
    private val academyRepository: AcademyRepository = mock(AcademyRepository::class.java)

    val observer: Observer<Resource<List<CourseEntity>>> = mock()

    @Before
    fun setUp() {
        viewModel = BookmarkViewModel(academyRepository)
    }

    @Test
    fun getBookmarks() {
        val resource: Resource<List<CourseEntity>> = Resource.success(FakeDataDummyTest.generateDummyCourses())
        val dummyCourses = MutableLiveData<Resource<List<CourseEntity>>>()
        dummyCourses.value = resource

        `when`(academyRepository.getBookmarkedCourses()).thenReturn(dummyCourses)

        viewModel?.getBookmarks()?.observeForever(observer)

        verify(observer).onChanged(resource)
    }
}