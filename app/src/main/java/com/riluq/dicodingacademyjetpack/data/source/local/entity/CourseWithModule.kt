package com.riluq.dicodingacademyjetpack.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation

class CourseWithModule() {
    @Embedded
    lateinit var course: CourseEntity

    @Relation(parentColumn = "courseId", entityColumn = "courseId")
    lateinit var modules: List<ModuleEntity>
}