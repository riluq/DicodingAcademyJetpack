package com.riluq.dicodingacademyjetpack.ui.bookmark

import com.riluq.dicodingacademyjetpack.data.CourseEntity

interface BookmarkFragmentCallback {
    fun onShareClick(course: CourseEntity)
}
