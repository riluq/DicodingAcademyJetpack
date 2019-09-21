package com.riluq.dicodingacademyjetpack.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.riluq.dicodingacademyjetpack.R
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ModuleEntity


class DetailCourseAdapter : RecyclerView.Adapter<DetailCourseAdapter.ModuleViewHolder>() {

    private val mModules: ArrayList<ModuleEntity> = arrayListOf()

    fun setModules(modules: List<ModuleEntity>?) {
        if (modules == null) return
        mModules.clear()
        mModules.addAll(modules)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.items_module_list, parent, false)
        return ModuleViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ModuleViewHolder, position: Int) {
        viewHolder.bind(mModules[position].title)
    }

    override fun getItemCount(): Int {
        return mModules.size
    }

    class ModuleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.text_module_title)

        fun bind(title: String?) {
            textTitle.text = title
        }
    }
}