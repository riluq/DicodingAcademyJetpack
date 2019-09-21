package com.riluq.dicodingacademyjetpack.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseWithModule
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ModuleEntity
import com.riluq.dicodingacademyjetpack.data.source.local.room.AcademyDao

class LocalRepository(private val academyDao: AcademyDao) {
    companion object {
        private var INSTANCE: LocalRepository? = null

        fun getInstance(academyDao: AcademyDao): LocalRepository {
            if (INSTANCE == null) {
                INSTANCE = LocalRepository(academyDao)
            }
            return INSTANCE!!
        }
    }

    fun getAllCourses(): LiveData<List<CourseEntity>> {
        return academyDao.getCourses()
    }

    fun getBookmarkedCourses(): LiveData<List<CourseEntity>> {
        return academyDao.getBookmarkedCourse()
    }

    fun getCourseWithModules(courseId: String): LiveData<CourseWithModule> {
        return academyDao.getCourseWithModuleById(courseId)
    }

    fun getAllModulesByCourse(courseId: String): LiveData<List<ModuleEntity>> {
        return academyDao.getModulesByCourseId(courseId)
    }

    fun insertCourses(courses: List<CourseEntity>) {
        academyDao.insertCourses(courses)
    }

    fun insertModules(modules: List<ModuleEntity>) {
        academyDao.insertModules(modules)
    }

    fun setCourseBookmark(course: CourseEntity, newState: Boolean) {
        course.isBookmarked = newState
        academyDao.updateCourse(course)
    }

    fun getModuleWithContent(moduleId: String): LiveData<ModuleEntity> {
        return academyDao.getModuleById(moduleId)
    }

    fun getBookmarkedCoursesPaged(): DataSource.Factory<Int, CourseEntity> {
        return academyDao.getBookmarkedCoursesAsPaged()
    }

    fun updateContent(content: String, moduleId: String) {
        academyDao.updateModuleByContent(content, moduleId)
    }

    fun setReadModule(module: ModuleEntity) {
        module.isRead = true
        academyDao.updateModule(module)
    }

}