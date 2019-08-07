package com.riluq.dicodingacademyjetpack.ui.detail

import androidx.lifecycle.ViewModel
import com.riluq.dicodingacademyjetpack.data.CourseEntity
import com.riluq.dicodingacademyjetpack.utils.generateDummyCourses
import com.riluq.dicodingacademyjetpack.data.ModuleEntity
import com.riluq.dicodingacademyjetpack.utils.generateDummyModules


class DetailCourseViewModel: ViewModel() {
    private var mCourse: CourseEntity? = null
    private var courseId: String? = null

    fun getCourse(): CourseEntity? {
        for (i in 0 until generateDummyCourses().size) {
            val courseEntity = generateDummyCourses()[i]
            if (courseEntity.courseId == courseId) {
                mCourse = courseEntity
            }
        }
        return mCourse
    }

    fun getModules(): MutableList<ModuleEntity>? {
        return getCourseId()?.let { generateDummyModules(it) }
    }

    fun setCourseId(courseId: String) {
        this.courseId = courseId
    }

    fun getCourseId(): String? {
        return courseId
    }
}