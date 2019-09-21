package com.riluq.dicodingacademyjetpack.utils

import java.util.concurrent.Executor
import java.util.concurrent.Executors


class DiskIOThreadExecutor : Executor {

    private val diskIO: Executor

    init {
        diskIO = Executors.newSingleThreadExecutor()
    }

    override fun execute(command: Runnable) {
        diskIO.execute(command)
    }
}