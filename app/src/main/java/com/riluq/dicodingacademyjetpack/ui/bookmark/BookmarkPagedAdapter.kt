package com.riluq.dicodingacademyjetpack.ui.bookmark

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.riluq.dicodingacademyjetpack.R
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.ui.detail.DetailCourseActivity
import com.riluq.dicodingacademyjetpack.utils.GlideApp

class BookmarkPagedAdapter(val callback: BookmarkFragmentCallback) :
    PagedListAdapter<CourseEntity, BookmarkPagedAdapter.BookmarkViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.items_bookmark, parent, false)
        return BookmarkViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val bookmark = getItem(position)
        if (bookmark != null) {
            holder.tvTitle.text = bookmark.title
            holder.tvDate.text = String.format("Deadline %s", bookmark.deadline)
            holder.tvDescription.text = bookmark.description
            holder.itemView.setOnClickListener { v ->
                val context = holder.itemView.context
                val intent = Intent(context, DetailCourseActivity::class.java)
                intent.putExtra(DetailCourseActivity.EXTRA_COURSE, bookmark.courseId)
                context.startActivity(intent)
            }

            holder.imgShare.setOnClickListener { v ->
                val course = CourseEntity(
                    bookmark.courseId,
                    bookmark.title,
                    bookmark.description,
                    bookmark.deadline,
                    false,
                    bookmark.imagePath
                )
                callback.onShareClick(course)
            }

            GlideApp.with(holder.itemView.context)
                .load(bookmark.imagePath)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(holder.imgPoster)
        }
    }

    class BookmarkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_item_title)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
        val tvDate: TextView = itemView.findViewById(R.id.tv_item_date)
        val imgShare: ImageButton = itemView.findViewById(R.id.img_share)
        val imgPoster: ImageView = itemView.findViewById(R.id.img_poster)
    }

    fun getItemById(swipedPosition: Int): CourseEntity? {
        return getItem(swipedPosition)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CourseEntity>() {
        override fun areItemsTheSame(oldItem: CourseEntity, newItem: CourseEntity): Boolean {
            return oldItem.courseId == newItem.courseId
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: CourseEntity, newItem: CourseEntity): Boolean {
            return oldItem == newItem
        }

    }
}