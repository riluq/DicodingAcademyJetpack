package com.riluq.dicodingacademyjetpack.utils

import java.util.concurrent.Executor

class InstantAppExecutors(instant: Executor = Executor { it.run() }): AppExecutors(instant, instant, instant)