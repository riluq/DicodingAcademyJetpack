package com.riluq.dicodingacademyjetpack.data.source.local.entity

import androidx.room.ColumnInfo

data class ContentEntity(
    @ColumnInfo(name = "content")
    val content: String?
)