package com.riluq.dicodingacademyjetpack.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "courseentity")
class CourseEntity(
    @field:PrimaryKey
    @field:ColumnInfo(name = "courseId")
    var courseId: String,
    @field:ColumnInfo(name = "title")
    var title: String?,
    @field:ColumnInfo(name = "description")
    var description: String?,
    @field:ColumnInfo(name = "deadline")
    var deadline: String?,
    bookmarked: Boolean?,
    @field:ColumnInfo(name = "imagePath")
    var imagePath: String?
) {

    @ColumnInfo(name = "bookmarked")
    var isBookmarked = false

    init {
        if (bookmarked != null) {
            this.isBookmarked = bookmarked
        }
    }
}
