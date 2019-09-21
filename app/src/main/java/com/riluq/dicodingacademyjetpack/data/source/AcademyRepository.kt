package com.riluq.dicodingacademyjetpack.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.riluq.dicodingacademyjetpack.data.source.local.LocalRepository
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseWithModule
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ModuleEntity
import com.riluq.dicodingacademyjetpack.data.source.remote.ApiResponse
import com.riluq.dicodingacademyjetpack.data.source.remote.RemoteRepository
import com.riluq.dicodingacademyjetpack.data.source.remote.response.ContentResponse
import com.riluq.dicodingacademyjetpack.data.source.remote.response.CourseResponse
import com.riluq.dicodingacademyjetpack.data.source.remote.response.ModuleResponse
import com.riluq.dicodingacademyjetpack.utils.AppExecutors
import com.riluq.dicodingacademyjetpack.vo.Resource


class AcademyRepository(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository,
    private val appExecutors: AppExecutors): AcademyDataSource {
    companion object {

        @Volatile
        private var INSTANCE: AcademyRepository? = null

        fun getInstance(localRepository: LocalRepository,
                        remoteData: RemoteRepository,
                        appExecutors: AppExecutors): AcademyRepository {
            if (INSTANCE == null) {
                synchronized(AcademyRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = AcademyRepository(localRepository, remoteData, appExecutors)
                    }
                }
            }
            return INSTANCE!!
        }

    }
    override fun getAllCourses(): LiveData<Resource<List<CourseEntity>>>? {
        return object : NetworkBoundResource<List<CourseEntity>, List<CourseResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<CourseEntity>> {
                return localRepository.getAllCourses()
            }

            override fun shouldFetch(data: List<CourseEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<CourseResponse>>> {
                return remoteRepository.getAllCoursesAsLiveData()
            }

            override fun saveCallResult(data: List<CourseResponse>?) {
                val courseEntity: ArrayList<CourseEntity> = ArrayList()

                for (courseResponse in data!!) {
                    courseEntity.add(CourseEntity(
                        courseResponse.id!!,
                        courseResponse.title,
                        courseResponse.description,
                        courseResponse.date,
                        null,
                        courseResponse.imagePath)
                    )
                }
                localRepository.insertCourses(courseEntity)
            }

        }.asLiveData()
    }

    override fun getCourseWithModules(courseId: String): LiveData<Resource<CourseWithModule>>? {
        return object : NetworkBoundResource<CourseWithModule, List<ModuleResponse>>(appExecutors){
            override fun loadFromDB(): LiveData<CourseWithModule> {
                return localRepository.getCourseWithModules(courseId)
            }

            override fun shouldFetch(data: CourseWithModule?): Boolean {
                return data?.modules == null || data.modules.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<ModuleResponse>>> {
                return remoteRepository.getAllModulesByCourseAsLiveData(courseId)
            }

            override fun saveCallResult(data: List<ModuleResponse>?) {
                val moduleEntities: ArrayList<ModuleEntity> = ArrayList()
                for (moduleResponse in data!!) {
                    moduleEntities.add(ModuleEntity(
                        moduleResponse.moduleId!!,
                        courseId,
                        moduleResponse.title!!,
                        moduleResponse.position!!,
                        null
                    ))
                }

                localRepository.insertModules(moduleEntities)
            }

        }.asLiveData()
    }
    override fun getAllModulesByCourse(courseId: String): LiveData<Resource<List<ModuleEntity>>>? {
        return object : NetworkBoundResource<List<ModuleEntity>, List<ModuleResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<ModuleEntity>>? {
                return localRepository.getAllModulesByCourse(courseId)
            }

            override fun shouldFetch(data: List<ModuleEntity>?): Boolean? {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<ModuleResponse>>>? {
                return remoteRepository.getAllModulesByCourseAsLiveData(courseId)
            }

            override fun saveCallResult(data: List<ModuleResponse>?) {
                val moduleEntities: ArrayList<ModuleEntity> = ArrayList()

                for (moduleResponse in data!!) {
                    moduleEntities.add(ModuleEntity(
                        moduleResponse.moduleId!!,
                        courseId,
                        moduleResponse.title!!,
                        moduleResponse.position!!,
                        null
                    ))
                }
                localRepository.insertModules(moduleEntities)
            }

        }.asLiveData()
    }

    override fun getBookmarkedCourses(): LiveData<Resource<List<CourseEntity>>>? {
        return object : NetworkBoundResource<List<CourseEntity>, List<CourseResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<CourseEntity>> {
                return localRepository.getBookmarkedCourses()
            }

            override fun shouldFetch(data: List<CourseEntity>?): Boolean {
                return false
            }

            override fun createCall(): LiveData<ApiResponse<List<CourseResponse>>>? {
                return null
            }

            override fun saveCallResult(data: List<CourseResponse>?) {

            }

        }.asLiveData()
    }

    override fun getContent(moduleId: String): LiveData<Resource<ModuleEntity>>? {
        return object : NetworkBoundResource<ModuleEntity, ContentResponse>(appExecutors){
            override fun loadFromDB(): LiveData<ModuleEntity>? {
                return localRepository.getModuleWithContent(moduleId)
            }

            override fun shouldFetch(data: ModuleEntity?): Boolean? {
                return data?.contentEntity == null
            }

            override fun createCall(): LiveData<ApiResponse<ContentResponse>>? {
                return remoteRepository.getContentAsLiveData(moduleId)
            }

            override fun saveCallResult(data: ContentResponse?) {
                localRepository.updateContent(data?.content!!, moduleId)
            }

        }.asLiveData()
    }

    override fun getBookmarkedCoursesPaged(): LiveData<Resource<PagedList<CourseEntity>>> {
        return object : NetworkBoundResource<PagedList<CourseEntity>, List<CourseResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<CourseEntity>>? {
                return LivePagedListBuilder(localRepository.getBookmarkedCoursesPaged(), 20).build()
            }

            override fun shouldFetch(data: PagedList<CourseEntity>?): Boolean? {
                return false
            }

            override fun createCall(): LiveData<ApiResponse<List<CourseResponse>>>? {
                return null
            }

            override fun saveCallResult(data: List<CourseResponse>?) {

            }

        }.asLiveData()
    }

    override fun setCourseBookmark(courseEntity: CourseEntity, state: Boolean) {
        val runnable = {
            localRepository.setCourseBookmark(courseEntity, state)
        }
        appExecutors.diskIO().execute(runnable)
    }

    override fun setReadModule(moduleEntity: ModuleEntity) {
        val runnable = {
            localRepository.setReadModule(moduleEntity)
        }
        appExecutors.diskIO().execute(runnable)
    }

}
