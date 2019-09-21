package com.riluq.dicodingacademyjetpack.ui.reader

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.riluq.dicodingacademyjetpack.data.source.AcademyRepository
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ModuleEntity


class CourseReaderViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    private var courseId = MutableLiveData<String>()
    private var moduleId = MutableLiveData<String>()

    val modules = Transformations.switchMap(courseId) { courseId ->
        academyRepository.getAllModulesByCourse(courseId)
    }

    fun setCourseId(courseId: String) {
        this.courseId.value = courseId
    }

    val selectedModule = Transformations.switchMap(moduleId) { selectedPosition ->
        academyRepository.getContent(selectedPosition)
    }

    fun setSelectedModule(moduleId: String) {
        this.moduleId.value = moduleId
    }

    fun readContent(module: ModuleEntity) {
        academyRepository.setReadModule(module)
    }

    fun getModuleSize(): Int {
        if (modules.value != null) {
            val moduleEntities = modules.value?.data
            if (moduleEntities != null) {
                return moduleEntities.size
            }
        }
        return 0
    }

    fun setNextPage() {
        if (selectedModule.value != null && modules.value != null) {
            val moduleEntity = selectedModule.value?.data
            val moduleEntities = modules.value?.data
            if (moduleEntity != null && moduleEntities != null) {
                val position = moduleEntity.position
                if (position!! < moduleEntities.size && position >= 0) {
                    moduleEntities[position + 1].moduleId?.let { setSelectedModule(it) }
                }
            }
        }
    }

    fun setPrevPage() {
        if (selectedModule.value != null && modules.value != null) {
            val moduleEntity = selectedModule.value?.data
            val moduleEntities = modules.value?.data
            if (moduleEntity != null && moduleEntities != null) {
                val position = moduleEntity.position
                if (position!! < moduleEntities.size && position >= 0) {
                    moduleEntities[position - 1].moduleId?.let { setSelectedModule(it) }
                }
            }
        }
    }

}