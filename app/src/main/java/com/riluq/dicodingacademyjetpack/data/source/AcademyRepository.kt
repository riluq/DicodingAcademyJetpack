package com.riluq.dicodingacademyjetpack.data.source

import com.riluq.dicodingacademyjetpack.data.source.local.entity.ContentEntity
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ModuleEntity
import com.riluq.dicodingacademyjetpack.data.source.remote.RemoteRepository


class AcademyRepository(val remoteRepository: RemoteRepository): AcademyDataSource {
    companion object {

        @Volatile
        private var INSTANCE: AcademyRepository? = null
        fun getInstance(remoteData: RemoteRepository): AcademyRepository {
            if (INSTANCE == null) {
                synchronized(AcademyRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = AcademyRepository(remoteData)
                    }
                }
            }
            return INSTANCE!!
        }

    }
    override fun getAllCourses(): List<CourseEntity>? {
        val courseResponses = remoteRepository.getAllCourses()
        val courseList: ArrayList<CourseEntity> = ArrayList()
        for (i in courseResponses.indices) {
            val (id, title, description, date, imagePath) = courseResponses[i]
            val course = CourseEntity(
                id,
                title,
                description,
                date,
                false,
                imagePath
            )

            courseList.add(course)
        }
        return courseList
    }

    override fun getCourseWithModules(courseId: String): CourseEntity? {
        var course: CourseEntity? = null
        val courses = remoteRepository.getAllCourses()
        for (i in courses.indices) {
            val (id, title, description, date, imagePath) = courses[i]
            if (id == courseId) {
                course = CourseEntity(
                    id,
                    title,
                    description,
                    date,
                    false,
                    imagePath
                )
            }
        }
        return course
    }

    override fun getAllModulesByCourse(courseId: String): List<ModuleEntity>? {
        val moduleList: ArrayList<ModuleEntity> = ArrayList()
        val moduleResponses = remoteRepository.getModules(courseId)
        for (i in moduleResponses.indices) {
            val (moduleId, courseId1, title, position) = moduleResponses[i]
            val course = ModuleEntity(
                moduleId,
                courseId1!!,
                title,
                position,
                false
            )

            moduleList.add(course)
        }

        return moduleList
    }

    override fun getBookmarkedCourses(): List<CourseEntity>? {
        val courseList: ArrayList<CourseEntity> = ArrayList()
        val courses = remoteRepository.getAllCourses()
        for (i in courses.indices) {
            val (id, title, description, date, imagePath) = courses[i]
            val course = CourseEntity(
                id,
                title,
                description,
                date,
                false,
                imagePath
            )
            courseList.add(course)
        }
        return courseList
    }

    override fun getContent(courseId: String, moduleId: String): ModuleEntity? {
        val moduleResponses = remoteRepository.getModules(courseId)

        var module: ModuleEntity? = null
        for (i in moduleResponses.indices) {
            val (id, courseId1, title, position) = moduleResponses[i]

            if (id == moduleId) {
                module = ModuleEntity(
                    id,
                    courseId1!!,
                    title,
                    position,
                    false
                )

                module.contentEntity =
                    ContentEntity(
                        remoteRepository.getContent(moduleId).content
                    )
                break
            }
        }

        return module
    }

}
