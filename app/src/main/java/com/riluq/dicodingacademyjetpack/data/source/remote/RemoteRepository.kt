package com.riluq.dicodingacademyjetpack.data.source.remote

import android.os.Handler
import com.riluq.dicodingacademyjetpack.data.source.remote.response.ContentResponse
import com.riluq.dicodingacademyjetpack.data.source.remote.response.CourseResponse
import com.riluq.dicodingacademyjetpack.data.source.remote.response.ModuleResponse
import com.riluq.dicodingacademyjetpack.utils.EspressoIdlingResource
import com.riluq.dicodingacademyjetpack.utils.JsonHelper

// Setelah proses pengujian selesai dan Anda akan membuat Proyek Academy menjadi sebuah APK,
// Anda harus menghilangkan Idling Resource agar aplikasi tidak mengalami memory leaks.
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
        EspressoIdlingResource.increment()
        val handler = Handler()
        handler.postDelayed(
            {
                callback.onAllCoursesReceived(jsonHelper.loadCourses())
                EspressoIdlingResource.decrement()
            },
            SERVICE_LATENCY_IN_MILLIS
        )
    }

    fun getModules(courseId: String, callback: LoadModuleCallback) {
        EspressoIdlingResource.increment()
        val handler = Handler()
        handler.postDelayed(
            {
                callback.onAllModulesReceived(jsonHelper.loadModule(courseId))
                EspressoIdlingResource.decrement()
            },
            SERVICE_LATENCY_IN_MILLIS
        )
    }

    fun getContent(moduleId: String, callback: GetContentCallback) {
        EspressoIdlingResource.increment()
        val handler = Handler()
        handler.postDelayed(
            {
                callback.onContentReceived(jsonHelper.loadContent(moduleId))
                EspressoIdlingResource.decrement()
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