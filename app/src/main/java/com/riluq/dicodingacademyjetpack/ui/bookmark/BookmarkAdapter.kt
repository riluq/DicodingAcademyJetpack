package com.riluq.dicodingacademyjetpack.ui.bookmark

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.riluq.dicodingacademyjetpack.R
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.ui.detail.DetailCourseActivity
import com.riluq.dicodingacademyjetpack.utils.GlideApp


class BookmarkAdapter(private val activity: Activity, private val callback: BookmarkFragmentCallback) :
    RecyclerView.Adapter<BookmarkAdapter.AcademyViewHolder>() {

    private val courses: ArrayList<CourseEntity> = arrayListOf()

    fun setListCourses(courses: List<CourseEntity>?) {
        if (courses == null) return
        this.courses.clear()
        this.courses.addAll(courses)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcademyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_bookmark, parent, false)
        return AcademyViewHolder(view)
    }

    override fun onBindViewHolder(holder: AcademyViewHolder, position: Int) {
        val course = courses[position]

        holder.tvTitle.text = course.title
        holder.tvDate.text = String.format("Deadline %s", course.deadline)
        holder.tvDescription.text = course.description
        holder.itemView.setOnClickListener { v ->
            val intent = Intent(activity, DetailCourseActivity::class.java)
            intent.putExtra(DetailCourseActivity.EXTRA_COURSE, course.courseId)
            activity.startActivity(intent)
        }
        holder.imgShare.setOnClickListener { v -> callback.onShareClick(courses[holder.adapterPosition]) }

        GlideApp.with(holder.itemView.context)
            .load(course.imagePath)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
            .into(holder.imgPoster)
    }

    override fun getItemCount(): Int = courses.size

    class AcademyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_item_title)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
        val tvDate: TextView = itemView.findViewById(R.id.tv_item_date)
        val imgShare: ImageButton = itemView.findViewById(R.id.img_share)
        val imgPoster: ImageView = itemView.findViewById(R.id.img_poster)

    }
}