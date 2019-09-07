package com.riluq.dicodingacademyjetpack.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ContentEntity
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ModuleEntity
import com.riluq.dicodingacademyjetpack.data.source.remote.RemoteRepository
import com.riluq.dicodingacademyjetpack.data.source.remote.response.ContentResponse
import com.riluq.dicodingacademyjetpack.data.source.remote.response.CourseResponse
import com.riluq.dicodingacademyjetpack.data.source.remote.response.ModuleResponse


class AcademyRepository(private val remoteRepository: RemoteRepository): AcademyDataSource {
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
    override fun getAllCourses(): LiveData<List<CourseEntity>>? {
        val courseResults = MutableLiveData<List<CourseEntity>>()

        remoteRepository.getAllCourses(object: RemoteRepository.LoadCourseCallback {
            override fun onAllCoursesReceived(courseResponses: List<CourseResponse>) {
                val courseList: ArrayList<CourseEntity> = ArrayList()
                for (element in courseResponses) {
                    val (id, title, description, date, imagePath) = element
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
                courseResults.postValue(courseList)
            }

            override fun onDataNotAvailable() {

            }

        })
        return courseResults
    }

    override fun getCourseWithModules(courseId: String): LiveData<CourseEntity>? {
        val courseResult = MutableLiveData<CourseEntity>()
        remoteRepository.getAllCourses(object : RemoteRepository.LoadCourseCallback {
            override fun onAllCoursesReceived(courseResponses: List<CourseResponse>) {
                for (element in courseResponses) {
                    val (id, title, description, date, imagePath) = element
                    if (id.equals(courseId)) {
                        val course = CourseEntity(
                            id,
                            title,
                            description,
                            date,
                            false,
                            imagePath
                        )
                        courseResult.postValue(course)
                    }
                }
            }

            override fun onDataNotAvailable() {

            }

        })
        return courseResult
    }

    override fun getAllModulesByCourse(courseId: String): LiveData<List<ModuleEntity>>? {
        val moduleResult = MutableLiveData<List<ModuleEntity>>()
        remoteRepository.getModules(courseId, object : RemoteRepository.LoadModuleCallback {
            override fun onAllModulesReceived(moduleResponses: List<ModuleResponse>) {
                val moduleList: ArrayList<ModuleEntity> = ArrayList()
                for (element in moduleResponses) {
                    val (moduleId, courseIdResponse, title, position) = element
                    val module = ModuleEntity(
                        moduleId,
                        courseIdResponse!!,
                        title,
                        position,
                        false
                    )
                    moduleList.add(module)
                }
                moduleResult.postValue(moduleList)
            }

            override fun onDataNotAvailable() {

            }

        })

        return moduleResult
    }

    override fun getBookmarkedCourses(): LiveData<List<CourseEntity>>? {
        val courseResult = MutableLiveData<List<CourseEntity>>()
        remoteRepository.getAllCourses(object : RemoteRepository.LoadCourseCallback {
            override fun onAllCoursesReceived(courseResponses: List<CourseResponse>) {
                val courseList: ArrayList<CourseEntity> = ArrayList()
                for (element in courseResponses) {
                    val (id, title, description, date, imagePath) = element
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
                courseResult.postValue(courseList)
            }

            override fun onDataNotAvailable() {

            }

        })
        return courseResult
    }

    override fun getContent(courseId: String, moduleId: String): LiveData<ModuleEntity>? {
        val moduleResult = MutableLiveData<ModuleEntity>()
        remoteRepository.getModules(courseId, object : RemoteRepository.LoadModuleCallback {
            override fun onAllModulesReceived(moduleResponses: List<ModuleResponse>) {
                val module: ModuleEntity
                for (element in moduleResponses) {
                    val (id, courseIdResponse, title, position) = element

                    if (id == moduleId) {
                        module = ModuleEntity(id, courseIdResponse!!, title, position, false)

                        remoteRepository.getContent(
                            moduleId,
                            object : RemoteRepository.GetContentCallback {
                                override fun onContentReceived(contentResponse: ContentResponse) {
                                    module.contentEntity = ContentEntity(contentResponse.content)
                                    moduleResult.postValue(module)
                                }

                                override fun onDataNotAvailable() {

                                }
                            })
                        break
                    }
                }
            }

            override fun onDataNotAvailable() {

            }

        })


        return moduleResult
    }

}
