package com.riluq.dicodingacademyjetpack.ui.academy

import androidx.lifecycle.ViewModel
import com.riluq.dicodingacademyjetpack.data.CourseEntity
import com.riluq.dicodingacademyjetpack.utils.generateDummyCourses

class AcademyViewModel: ViewModel() {
    fun getCourses(): MutableList<CourseEntity> {
        return generateDummyCourses()
    }
}