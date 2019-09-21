package com.riluq.dicodingacademyjetpack.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.riluq.dicodingacademyjetpack.data.source.AcademyRepository
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseWithModule
import com.riluq.dicodingacademyjetpack.vo.Resource


class DetailCourseViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    private var mCourse: CourseEntity? = null
    private var courseId = MutableLiveData<String>()

    val courseModule: LiveData<Resource<CourseWithModule>> =
        Transformations.switchMap(courseId) { courseId ->
            academyRepository.getCourseWithModules(courseId)
        }

    fun setCourseId(courseId: String) {
        this.courseId.value = courseId
    }

    fun getCourseId(): String? {
        if (courseId.value == null) return null
        return courseId.value
    }

    fun setBookmark() {
        if (courseModule.value != null) {
            val courseWithModule = courseModule.value?.data

            if (courseWithModule != null) {
                val courseEntity = courseWithModule.course

                // Kode dibawah menggunakan tanda seru
                // karena akan mengganti status dari apakah sudah atau tidak menjadi apakah sudah siap dibookmark atau tidak
                val newState = courseEntity.isBookmarked.not()
                academyRepository.setCourseBookmark(courseEntity, newState)
            }
        }
    }
}