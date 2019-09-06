package com.riluq.dicodingacademyjetpack.ui.bookmark

import com.riluq.dicodingacademyjetpack.data.source.AcademyRepository
import com.riluq.dicodingacademyjetpack.utils.FakeDataDummyTest
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.*

class BookmarkViewModelTest {

    private var viewModel: BookmarkViewModel? = null
    private val academyRepository: AcademyRepository = mock(AcademyRepository::class.java)

    @Before
    fun setUp() {
        viewModel = BookmarkViewModel(academyRepository)
    }

    @Test
    fun getBookmarks() {
        `when`(academyRepository.getBookmarkedCourses()).thenReturn(FakeDataDummyTest.generateDummyCourses())
        val bookmarkEntity = viewModel?.getBookmarks()
        verify(academyRepository).getBookmarkedCourses()
        assertNotNull(bookmarkEntity)
        assertEquals(5, bookmarkEntity?.size)
    }
}