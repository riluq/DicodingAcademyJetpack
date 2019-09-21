package com.riluq.dicodingacademyjetpack.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.riluq.dicodingacademyjetpack.data.source.AcademyRepository
import com.riluq.dicodingacademyjetpack.di.Injection
import com.riluq.dicodingacademyjetpack.ui.academy.AcademyViewModel
import com.riluq.dicodingacademyjetpack.ui.bookmark.BookmarkViewModel
import com.riluq.dicodingacademyjetpack.ui.detail.DetailCourseViewModel
import com.riluq.dicodingacademyjetpack.ui.reader.CourseReaderViewModel

class ViewModelFactory(val academyRepository: AcademyRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = ViewModelFactory(Injection.provideRepository(application))
                    }
                }
            }
            return INSTANCE!!
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AcademyViewModel::class.java)) {
            return AcademyViewModel(academyRepository) as T
        } else if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
            return BookmarkViewModel(academyRepository) as T
        } else if (modelClass.isAssignableFrom(DetailCourseViewModel::class.java)) {
            return DetailCourseViewModel(academyRepository) as T
        } else if (modelClass.isAssignableFrom(CourseReaderViewModel::class.java)) {
            return CourseReaderViewModel(academyRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}