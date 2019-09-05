package com.riluq.dicodingacademyjetpack.data.source

import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ModuleEntity

interface AcademyDataSource {

    fun getAllCourses(): List<CourseEntity>?

    fun getCourseWithModules(courseId: String): CourseEntity?

    fun getAllModulesByCourse(courseId: String): List<ModuleEntity>?

    fun getBookmarkedCourses(): List<CourseEntity>?

    fun getContent(courseId: String, moduleId: String): ModuleEntity?

}