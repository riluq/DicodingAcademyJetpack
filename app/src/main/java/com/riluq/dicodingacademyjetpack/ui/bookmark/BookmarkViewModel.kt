package com.riluq.dicodingacademyjetpack.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.riluq.dicodingacademyjetpack.data.source.AcademyRepository
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.utils.generateDummyCourses
import com.riluq.dicodingacademyjetpack.vo.Resource

class BookmarkViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    fun getBookmarks(): LiveData<Resource<List<CourseEntity>>> {
        return academyRepository.getBookmarkedCourses()!!
    }
}