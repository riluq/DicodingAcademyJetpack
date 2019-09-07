package com.riluq.dicodingacademyjetpack.ui.reader

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.riluq.dicodingacademyjetpack.data.source.AcademyRepository
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ContentEntity
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ModuleEntity
import com.riluq.dicodingacademyjetpack.utils.generateDummyModules


class CourseReaderViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    private var courseId: String? = null
    private var moduleId: String? = null

    fun getModules(): LiveData<List<ModuleEntity>>? {
        return academyRepository.getAllModulesByCourse(courseId!!)
    }

    fun setCourseId(courseId: String) {
        this.courseId = courseId
    }

    fun getSelectedModule(): LiveData<ModuleEntity>? {
        return academyRepository.getContent(courseId!!, moduleId!!)
    }

    fun setSelectedModule(moduleId: String) {
        this.moduleId = moduleId
    }
}