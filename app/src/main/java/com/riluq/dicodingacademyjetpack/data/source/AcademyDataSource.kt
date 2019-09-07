package com.riluq.dicodingacademyjetpack.data.source

import androidx.lifecycle.LiveData
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ModuleEntity

interface AcademyDataSource {

    fun getAllCourses(): LiveData<List<CourseEntity>>?

    fun getCourseWithModules(courseId: String): LiveData<CourseEntity>?

    fun getAllModulesByCourse(courseId: String): LiveData<List<ModuleEntity>>?

    fun getBookmarkedCourses(): LiveData<List<CourseEntity>>?

    fun getContent(courseId: String, moduleId: String): LiveData<ModuleEntity>?

}