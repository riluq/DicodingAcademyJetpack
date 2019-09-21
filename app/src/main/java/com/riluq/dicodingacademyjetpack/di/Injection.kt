package com.riluq.dicodingacademyjetpack.di

import android.app.Application
import com.riluq.dicodingacademyjetpack.data.source.AcademyRepository
import com.riluq.dicodingacademyjetpack.data.source.local.LocalRepository
import com.riluq.dicodingacademyjetpack.data.source.local.room.AcademyDatabase
import com.riluq.dicodingacademyjetpack.data.source.remote.RemoteRepository
import com.riluq.dicodingacademyjetpack.utils.AppExecutors
import com.riluq.dicodingacademyjetpack.utils.JsonHelper

class Injection {
    companion object {
        fun provideRepository(application: Application): AcademyRepository {
            val database = AcademyDatabase.getInstance(application)

            val localRepository = LocalRepository.getInstance(database.academyDao())
            val remoteRepository = RemoteRepository.getInstance(JsonHelper(application))
            val appExecutors = AppExecutors()

            return AcademyRepository.getInstance(localRepository, remoteRepository, appExecutors)
        }
    }
}