package com.riluq.dicodingacademyjetpack.data.source.remote

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.riluq.dicodingacademyjetpack.data.source.remote.response.ContentResponse
import com.riluq.dicodingacademyjetpack.data.source.remote.response.CourseResponse
import com.riluq.dicodingacademyjetpack.data.source.remote.response.ModuleResponse
import com.riluq.dicodingacademyjetpack.utils.EspressoIdlingResource
import com.riluq.dicodingacademyjetpack.utils.JsonHelper

// Setelah proses pengujian selesai dan Anda akan membuat Proyek Academy menjadi sebuah APK,
// Anda harus menghilangkan Idling Resource agar aplikasi tidak mengalami memory leaks.
class RemoteRepository(private val jsonHelper: JsonHelper) {
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

    fun getAllCoursesAsLiveData(): LiveData<ApiResponse<List<CourseResponse>>> {
        EspressoIdlingResource.increment()
        val resultCourse = MutableLiveData<ApiResponse<List<CourseResponse>>>()

        val handler = Handler()
        handler.postDelayed(
            {
                resultCourse.value = ApiResponse.success(jsonHelper.loadCourses())
                if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                    EspressoIdlingResource.decrement()
                }
            },
            SERVICE_LATENCY_IN_MILLIS
        )
        return resultCourse
    }

    fun getAllModulesByCourseAsLiveData(courseId: String): LiveData<ApiResponse<List<ModuleResponse>>> {
        EspressoIdlingResource.increment()
        val resultModules = MutableLiveData<ApiResponse<List<ModuleResponse>>>()

        val handler = Handler()
        handler.postDelayed(
            {
                resultModules.value = ApiResponse.success(jsonHelper.loadModule(courseId))
                if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                    EspressoIdlingResource.decrement()
                }
            },
            SERVICE_LATENCY_IN_MILLIS
        )
        return resultModules
    }

    fun getContentAsLiveData(moduleId: String): LiveData<ApiResponse<ContentResponse>> {
        EspressoIdlingResource.increment()
        val resultContent = MutableLiveData<ApiResponse<ContentResponse>>()
        val handler = Handler()
        handler.postDelayed(
            {
                resultContent.value = ApiResponse.success(jsonHelper.loadContent(moduleId))
                if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                    EspressoIdlingResource.decrement()
                }
            },
            SERVICE_LATENCY_IN_MILLIS
        )
        return resultContent
    }

}