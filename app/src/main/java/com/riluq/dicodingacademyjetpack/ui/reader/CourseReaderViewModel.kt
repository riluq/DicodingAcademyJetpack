package com.riluq.dicodingacademyjetpack.ui.reader

import androidx.lifecycle.ViewModel
import com.riluq.dicodingacademyjetpack.data.ContentEntity
import com.riluq.dicodingacademyjetpack.data.ModuleEntity
import com.riluq.dicodingacademyjetpack.utils.generateDummyModules


class CourseReaderViewModel: ViewModel() {
    private var courseId: String? = null
    private var moduleId: String? = null

    fun getModules(): MutableList<ModuleEntity>? {
        return courseId?.let { generateDummyModules(it) }
    }

    fun setCourseId(courseId: String) {
        this.courseId = courseId
    }

    fun getSelectedModule(): ModuleEntity? {
        var module: ModuleEntity? = null
        for (i in 0 until getModules()!!.size) {
            if (getModules()?.get(i)?.moduleId.equals(moduleId)) {
                module = getModules()?.get(i)

                module?.contentEntity =
                    ContentEntity("<h3 class=\\\"fr-text-bordered\\\">" + module?.title + "</h3><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>")
                break
            }
        }
        return module
    }

    fun setSelectedModule(moduleId: String) {
        this.moduleId = moduleId
    }
}