package com.riluq.dicodingacademyjetpack.ui.academy

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.riluq.dicodingacademyjetpack.data.CourseEntity
import com.riluq.dicodingacademyjetpack.ui.detail.DetailCourseActivity
import com.riluq.dicodingacademyjetpack.utils.GlideApp
import com.riluq.dicodingacademyjetpack.R

class AcademyAdapter internal constructor(private val activity: Activity) :
    RecyclerView.Adapter<AcademyAdapter.AcademyViewHolder>() {

    private val mCourses: ArrayList<CourseEntity> = arrayListOf()

    private fun getListCourses(): List<CourseEntity> {
        return mCourses
    }

    fun setListCourses(listCourses: List<CourseEntity>?) {
        if (listCourses == null) return
        this.mCourses.clear()
        this.mCourses.addAll(listCourses)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcademyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.items_academy, parent, false)
        return AcademyViewHolder(view)
    }

    override fun onBindViewHolder(holder: AcademyViewHolder, position: Int) {
        holder.tvTitle.text = getListCourses()[position].title
        holder.tvDescription.text = getListCourses()[position].description
        holder.tvDate.text = String.format("Deadline %s", getListCourses()[position].deadline)
        holder.itemView.setOnClickListener { v ->
            val intent = Intent(activity, DetailCourseActivity::class.java)
            intent.putExtra(DetailCourseActivity.EXTRA_COURSE, getListCourses()[position].courseId)
            activity.startActivity(intent)
        }

        GlideApp.with(holder.itemView.context)
            .load(getListCourses()[position].imagePath)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading).error(
                    R.drawable.ic_error
                )
            )
            .into(holder.imgPoster)
    }

    override fun getItemCount(): Int = getListCourses().size

    class AcademyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView =
            itemView.findViewById(R.id.tv_item_title)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
        val tvDate: TextView =
            itemView.findViewById(R.id.tv_item_date)
        val imgPoster: ImageView =
            itemView.findViewById(R.id.img_poster)

    }
}