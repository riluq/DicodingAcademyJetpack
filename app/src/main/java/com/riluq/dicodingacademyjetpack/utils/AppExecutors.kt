package com.riluq.dicodingacademyjetpack.utils

import android.os.Handler
import androidx.annotation.MainThread
import androidx.annotation.VisibleForTesting
import java.util.concurrent.Executor
import androidx.annotation.NonNull
import android.os.Looper
import java.util.concurrent.Executors


class AppExecutors @VisibleForTesting
constructor(
    private val diskIO: Executor,
    private val networkIO: Executor,
    private val mainThread: Executor
) {

    constructor(): this(
        DiskIOThreadExecutor(), Executors.newFixedThreadPool(THREAD_COUNT), MainThreadExecutor()
    )

    fun diskIO(): Executor {
        return diskIO
    }

    fun networkIO(): Executor {
        return networkIO
    }

    fun mainThread(): Executor {
        return mainThread
    }

    companion object {
        private const val THREAD_COUNT: Int = 3

        private class MainThreadExecutor: Executor {
            private val mainThreadHandler: Handler = Handler(Looper.getMainLooper())

            override fun execute(command: Runnable) {
                mainThreadHandler.post(command)
            }

        }
    }

}