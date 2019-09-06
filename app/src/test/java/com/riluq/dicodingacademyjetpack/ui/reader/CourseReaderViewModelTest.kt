package com.riluq.dicodingacademyjetpack.ui.reader

import com.riluq.dicodingacademyjetpack.data.source.AcademyRepository
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ContentEntity
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ModuleEntity
import com.riluq.dicodingacademyjetpack.utils.FakeDataDummyTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*


class CourseReaderViewModelTest {

    private var viewModel: CourseReaderViewModel? = null
    private var academyRepository = mock(AcademyRepository::class.java)
    private var dummyCourse: CourseEntity? = FakeDataDummyTest.generateDummyCourses()[0]
    private var courseId = dummyCourse?.courseId
    private var dummyModule: List<ModuleEntity> = FakeDataDummyTest.generateDummyModules(courseId!!)
    private var moduleId = dummyModule[0].moduleId

    @Before
    fun setUp() {
        viewModel = CourseReaderViewModel(academyRepository)
        viewModel!!.setCourseId(courseId!!)
    }

    @Test
    fun getModules() {
        `when`(academyRepository.getAllModulesByCourse(courseId!!)).thenReturn(dummyModule)
        val moduleEntities = viewModel?.getModules()
        verify(academyRepository).getAllModulesByCourse(courseId!!)
        assertNotNull(moduleEntities)
        assertEquals(7, moduleEntities?.size)
    }

    @Test
    fun getSelectedModule() {

        val moduleEntity = dummyModule[0]
        val content =
            "<h3 class=\"fr-text-bordered\">Modul 0 : Introduction</h3><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>"
        moduleEntity.contentEntity = ContentEntity(content)
        viewModel?.setSelectedModule(moduleId!!)

        `when`<ModuleEntity>(academyRepository.getContent(courseId!!, moduleId!!)).thenReturn(
            moduleEntity
        )
        val entity = viewModel?.getSelectedModule()
        verify(academyRepository).getContent(courseId!!, moduleId!!)
        assertNotNull(entity)

        val contentEntity = entity!!.contentEntity
        assertNotNull(contentEntity)

        val resultContent = contentEntity!!.content
        assertNotNull(resultContent)

        assertEquals(content, resultContent)
    }
}