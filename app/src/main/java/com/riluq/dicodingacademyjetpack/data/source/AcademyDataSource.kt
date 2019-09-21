package com.riluq.dicodingacademyjetpack.data.source

import androidx.lifecycle.LiveData
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseWithModule
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ModuleEntity
import com.riluq.dicodingacademyjetpack.vo.Resource

interface AcademyDataSource {

    fun getAllCourses(): LiveData<Resource<List<CourseEntity>>>?

    fun getCourseWithModules(courseId: String): LiveData<Resource<CourseWithModule>>?

    fun getAllModulesByCourse(courseId: String): LiveData<Resource<List<ModuleEntity>>>?

    fun getBookmarkedCourses(): LiveData<Resource<List<CourseEntity>>>?

    fun getContent(moduleId: String): LiveData<Resource<ModuleEntity>>?

    fun setCourseBookmark(courseEntity: CourseEntity, state: Boolean)

    fun setReadModule(moduleEntity: ModuleEntity)

}