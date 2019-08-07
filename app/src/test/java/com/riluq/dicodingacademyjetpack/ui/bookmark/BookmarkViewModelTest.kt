package com.riluq.dicodingacademyjetpack.ui.bookmark

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class BookmarkViewModelTest {

    private var viewModel: BookmarkViewModel? = null

    @Before
    fun setUp() {
        viewModel = BookmarkViewModel()
    }

    @Test
    fun getBookmarks() {
        val bookmarkEntity = viewModel?.getBookmarks()
        assertNotNull(bookmarkEntity)
        assertEquals(5, bookmarkEntity?.size)
    }
}