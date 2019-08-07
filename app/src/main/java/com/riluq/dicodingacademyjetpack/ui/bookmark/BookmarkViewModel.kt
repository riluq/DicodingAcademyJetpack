package com.riluq.dicodingacademyjetpack.ui.bookmark

import androidx.lifecycle.ViewModel
import com.riluq.dicodingacademyjetpack.data.CourseEntity
import com.riluq.dicodingacademyjetpack.utils.generateDummyCourses

class BookmarkViewModel: ViewModel() {
    fun getBookmarks(): MutableList<CourseEntity> {
        return generateDummyCourses()
    }
}