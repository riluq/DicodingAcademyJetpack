package com.riluq.dicodingacademyjetpack.ui.bookmark

import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity

interface BookmarkFragmentCallback {
    fun onShareClick(course: CourseEntity)
}
