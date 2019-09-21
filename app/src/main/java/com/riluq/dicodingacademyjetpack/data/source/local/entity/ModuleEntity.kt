package com.riluq.dicodingacademyjetpack.data.source.local.entity

import androidx.room.*


@Entity(
    tableName = "moduleentities",
    primaryKeys = ["moduleId", "courseId"],
    foreignKeys = [ForeignKey(
        entity = CourseEntity::class,
        parentColumns = ["courseId"],
        childColumns = ["courseId"]
    )],
    indices = [Index(value = ["moduleId"]), Index(value = ["courseId"])]
)
class ModuleEntity(
    @field:ColumnInfo(name = "moduleId")
    var moduleId: String, courseId: String, @field:ColumnInfo(name = "title")
    var title: String, @field:ColumnInfo(name = "position")
    var position: Int, read: Boolean?
) {
    @Embedded
    var contentEntity: ContentEntity? = null

    @ColumnInfo(name = "courseId")
    var courseId: String
        private set

    @ColumnInfo(name = "read")
    var isRead = false

    init {
        this.courseId = courseId

        if (read != null) {
            this.isRead = read
        }
    }

    fun setIdCourse(courseId: String) {
        this.courseId = courseId
    }
}