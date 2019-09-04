package com.riluq.dicodingacademyjetpack.utils

import android.app.Application
import com.riluq.dicodingacademyjetpack.data.source.remote.response.CourseResponse
import com.riluq.dicodingacademyjetpack.data.source.remote.response.ModuleResponse
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class JsonHelper(val application: Application) {

    private fun parsingFileToString(fileName: String): String? {
        try {
            val inputStream = application.assets.open(fileName)
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            inputStream.close()
            return String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
    }

    fun loadCourses(): List<CourseResponse> {
        val list: ArrayList<CourseResponse> = ArrayList()
        try {
            val responseObject = JSONObject(parsingFileToString("CourseResponses.json")!!)
            val listArray = responseObject.getJSONArray("courses")
            for (i in 0 until listArray.length()) {
                val course = listArray.getJSONObject(i)

                val id = course.getString("id")
                val title = course.getString("title")
                val description = course.getString("description")
                val date = course.getString("date")
                val imagePath = course.getString("imagePath")

                val courseResponse = CourseResponse(id, title, description, date, imagePath)
                list.add(courseResponse)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return list
    }

//    fun loadModule(courseId: String): List<ModuleResponse> {
//        val fileName = String.format("Module_%s.json", courseId)
//        val list: ArrayList<ModuleResponse> = ArrayList()
//
//    }
}