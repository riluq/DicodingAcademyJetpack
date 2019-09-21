package com.riluq.dicodingacademyjetpack.data.source.local.room

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseWithModule
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ModuleEntity


@Dao
interface AcademyDao {

    @WorkerThread
    @Query("SELECT * FROM courseentity")
    fun getCourses(): LiveData<List<CourseEntity>>

    @WorkerThread
    @Query("SELECT * FROM courseentity where bookmarked = 1")
    fun getBookmarkedCourse(): LiveData<List<CourseEntity>>

    @Transaction
    @Query("SELECT * FROM courseentity WHERE courseId = :courseId")
    fun getCourseWithModuleById(courseId: String): LiveData<CourseWithModule>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCourses(courses: List<CourseEntity>): LongArray

    @Update(onConflict = OnConflictStrategy.FAIL)
    fun updateCourse(course: CourseEntity): Int

    @Query("SELECT * FROM moduleentities WHERE courseId = :courseId")
    fun getModulesByCourseId(courseId: String): LiveData<List<ModuleEntity>>

    @Query("SELECT * FROM moduleentities WHERE moduleId = :moduleId")
    fun getModuleById(moduleId: String): LiveData<ModuleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertModules(modules: List<ModuleEntity>)

    @Update
    fun updateModule(module: ModuleEntity): Int

    @Query("UPDATE moduleentities SET content = :content WHERE moduleId = :moduleId")
    fun updateModuleByContent(content: String, moduleId: String): Int

    @Query("SELECT * FROM courseentity where bookmarked = 1")
    fun getBookmarkedCoursesAsPaged(): DataSource.Factory<Int, CourseEntity>

}