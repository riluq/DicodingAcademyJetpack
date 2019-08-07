package com.riluq.dicodingacademyjetpack.ui.reader

import com.riluq.dicodingacademyjetpack.data.ContentEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test


class CourseReaderViewModelTest {

    private var viewModel: CourseReaderViewModel? = null
    private var dummyContentEntity: ContentEntity? = null
    private var moduleId: String? = null

    @Before
    fun setUp() {
        viewModel = CourseReaderViewModel()
        viewModel!!.setCourseId("a14")

        moduleId = "a14m1"
        val title = viewModel!!.getModules()?.get(0)?.title
        dummyContentEntity =
            ContentEntity("<h3 class=\\\"fr-text-bordered\\\">$title</h3><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>")

    }

    @Test
    fun getModules() {
        val moduleEntities = viewModel?.getModules()
        assertNotNull(moduleEntities)
        assertEquals(7, moduleEntities?.size)
    }

    @Test
    fun getSelectedModule() {
        moduleId?.let { viewModel?.setSelectedModule(it) }
        val moduleEntity = viewModel?.getSelectedModule()
        assertNotNull(moduleEntity)
        val contentEntity = moduleEntity?.contentEntity
        assertNotNull(contentEntity)
        val content = contentEntity!!.content
        assertNotNull(content)
        assertEquals(content, dummyContentEntity?.content)
    }
}