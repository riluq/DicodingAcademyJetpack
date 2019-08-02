package com.riluq.dicodingacademyjetpack.data

class ModuleEntity(var moduleId: String?, courseId: String, var title: String?, var position: Int?, read: Boolean?) {
    var contentEntity: ContentEntity? = null
    var courseId: String? = null
        private set
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