package com.riluq.dicodingacademyjetpack.data.source.remote

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.annotation.Nullable


class ApiResponse<T>(val statusResponse: StatusResponse?, val body: T?, val message: String?) {
    companion object {
        fun <T> success(@Nullable body: T): ApiResponse<T> {
            return ApiResponse(StatusResponse.SUCCESS, body, null)
        }
    }
}