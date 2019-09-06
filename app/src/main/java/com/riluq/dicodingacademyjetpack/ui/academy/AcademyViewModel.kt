package com.riluq.dicodingacademyjetpack.ui.academy

import androidx.lifecycle.ViewModel
import com.riluq.dicodingacademyjetpack.data.source.AcademyRepository
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity

class AcademyViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    fun getCourses(): List<CourseEntity> {
        return academyRepository.getAllCourses()!!
    }
}