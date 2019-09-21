package com.riluq.dicodingacademyjetpack.utils

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

// Setelah proses pengujian selesai dan Anda akan membuat Proyek Academy menjadi sebuah APK,
// Anda harus menghilangkan Idling Resource agar aplikasi tidak mengalami memory leaks.
class EspressoIdlingResource {
    companion object {
        private const val RESOURCE = "GLOBAL"
        private val espressoTestIdlingResource = CountingIdlingResource(RESOURCE)

        fun increment() {
            espressoTestIdlingResource.increment()
        }

        fun decrement() {
            espressoTestIdlingResource.decrement()
        }

        fun getEspressoIdlingResource(): IdlingResource {
            return espressoTestIdlingResource
        }
    }
}