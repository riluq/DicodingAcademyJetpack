package com.riluq.dicodingacademyjetpack.ui.academy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.riluq.dicodingacademyjetpack.data.source.AcademyRepository
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.vo.Resource


class AcademyViewModel(private val academyRepository: AcademyRepository) : ViewModel() {

    private val login = MutableLiveData<String>()

    val courses: LiveData<Resource<List<CourseEntity>>> = Transformations.switchMap(login) { data ->
        academyRepository.getAllCourses()
    }

    fun setUsername(username: String) {
        login.value = username
    }

}