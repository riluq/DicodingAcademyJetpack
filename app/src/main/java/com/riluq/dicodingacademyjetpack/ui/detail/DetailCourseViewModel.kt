package com.riluq.dicodingacademyjetpack.ui.detail

import androidx.lifecycle.ViewModel
import com.riluq.dicodingacademyjetpack.data.source.AcademyRepository
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.utils.generateDummyCourses
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ModuleEntity
import com.riluq.dicodingacademyjetpack.utils.generateDummyModules


class DetailCourseViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    private var mCourse: CourseEntity? = null
    private var courseId: String? = null

    fun getCourse(): CourseEntity? {
        return academyRepository.getCourseWithModules(courseId!!)
    }

    fun getModules(): List<ModuleEntity>? {
        return academyRepository.getAllModulesByCourse(courseId!!)
    }

    fun setCourseId(courseId: String) {
        this.courseId = courseId
    }

    fun getCourseId(): String? {
        return courseId
    }
}