package com.riluq.dicodingacademyjetpack.data

class CourseEntity(
    var courseId: String?,
    var title: String?,
    var description: String?,
    var deadline: String?,
    bookmarked: Boolean?,
    var imagePath: String?
) {
    var isBookmarked = false

    init {
        if (bookmarked != null) {
            this.isBookmarked = bookmarked
        }
    }
}