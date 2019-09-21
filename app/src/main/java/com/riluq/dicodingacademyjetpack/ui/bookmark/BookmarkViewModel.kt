package com.riluq.dicodingacademyjetpack.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.riluq.dicodingacademyjetpack.data.source.AcademyRepository
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.utils.generateDummyCourses
import com.riluq.dicodingacademyjetpack.vo.Resource

class BookmarkViewModel(private val academyRepository: AcademyRepository) : ViewModel() {

    fun setBookmark(courseEntity: CourseEntity) {
        val newState = courseEntity.isBookmarked.not()
        academyRepository.setCourseBookmark(courseEntity, newState)
    }

    fun getBookmarksPaged(): LiveData<Resource<PagedList<CourseEntity>>> {
        return academyRepository.getBookmarkedCoursesPaged()
    }
}