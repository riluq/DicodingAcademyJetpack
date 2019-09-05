package com.riluq.dicodingacademyjetpack.di

import android.app.Application
import com.riluq.dicodingacademyjetpack.data.source.AcademyRepository
import com.riluq.dicodingacademyjetpack.data.source.remote.RemoteRepository
import com.riluq.dicodingacademyjetpack.utils.JsonHelper

class Injection {
    companion object {
        fun provideRepository(application: Application): AcademyRepository {
            val remoteRepository = RemoteRepository.getInstance(JsonHelper(application))
            return AcademyRepository(remoteRepository)
        }
    }
}