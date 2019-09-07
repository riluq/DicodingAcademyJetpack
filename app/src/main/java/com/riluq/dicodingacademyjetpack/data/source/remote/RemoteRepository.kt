package com.riluq.dicodingacademyjetpack.data.source.remote

import android.os.Handler
import com.riluq.dicodingacademyjetpack.data.source.remote.response.ContentResponse
import com.riluq.dicodingacademyjetpack.data.source.remote.response.CourseResponse
import com.riluq.dicodingacademyjetpack.data.source.remote.response.ModuleResponse
import com.riluq.dicodingacademyjetpack.utils.JsonHelper


class RemoteRepository(val jsonHelper: JsonHelper) {
    companion object {
        private var INSTANCE: RemoteRepository? = null

        fun getInstance(jsonHelper: JsonHelper): RemoteRepository {
            if (INSTANCE == null) {
                INSTANCE = RemoteRepository(jsonHelper)
            }
            return INSTANCE!!
        }
    }
    private val SERVICE_LATENCY_IN_MILLIS: Long = 2000

    fun getAllCourses(callback: LoadCourseCallback) {
        val handler = Handler()
        handler.postDelayed(
            {
                callback.onAllCoursesReceived(jsonHelper.loadCourses())
            },
            SERVICE_LATENCY_IN_MILLIS
        )
    }

    fun getModules(courseId: String, callback: LoadModuleCallback) {
        val handler = Handler()
        handler.postDelayed(
            {
                callback.onAllModulesReceived(jsonHelper.loadModule(courseId))
            },
            SERVICE_LATENCY_IN_MILLIS
        )
    }

    fun getContent(moduleId: String, callback: GetContentCallback) {
        val handler = Handler()
        handler.postDelayed(
            {
                callback.onContentReceived(jsonHelper.loadContent(moduleId))
            },
            SERVICE_LATENCY_IN_MILLIS
        )
    }

    interface LoadCourseCallback {
        fun onAllCoursesReceived(courseResponses: List<CourseResponse>)

        fun onDataNotAvailable()
    }

    interface LoadModuleCallback {
        fun onAllModulesReceived(moduleResponses: List<ModuleResponse>)

        fun onDataNotAvailable()
    }

    interface GetContentCallback {
        fun onContentReceived(contentResponses: ContentResponse)

        fun onDataNotAvailable()
    }

}