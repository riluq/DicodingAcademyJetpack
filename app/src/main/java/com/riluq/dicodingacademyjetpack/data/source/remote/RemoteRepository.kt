package com.riluq.dicodingacademyjetpack.data.source.remote

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

    fun getAllCourses(): List<CourseResponse> {
        return jsonHelper.loadCourses()
    }

    fun getModules(courseId: String): List<ModuleResponse> {
        return jsonHelper.loadModule(courseId)
    }

    fun getContent(moduleId: String): ContentResponse {
        return jsonHelper.loadContent(moduleId)
    }

}