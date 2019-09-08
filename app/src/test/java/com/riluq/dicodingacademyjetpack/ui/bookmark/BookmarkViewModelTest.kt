package com.riluq.dicodingacademyjetpack.ui.bookmark

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.riluq.dicodingacademyjetpack.data.source.AcademyRepository
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.utils.FakeDataDummyTest
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

    val observer: Observer<List<CourseEntity>> = mock()

    @Before
    fun setUp() {
        viewModel = BookmarkViewModel(academyRepository)
    }

    @Test
    fun getBookmarks() {
        val dummyCourses: List<CourseEntity> = FakeDataDummyTest.generateDummyCourses()

        val courses = MutableLiveData<List<CourseEntity>>()
        courses.value = dummyCourses

        `when`(academyRepository.getBookmarkedCourses()).thenReturn(courses)

        viewModel?.getBookmarks()?.observeForever(observer)

        verify(observer).onChanged(dummyCourses)
    }
}