package com.riluq.dicodingacademyjetpack.ui.reader.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.riluq.dicodingacademyjetpack.R
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ModuleEntity


class ModuleListAdapter internal constructor(private val listener: MyAdapterClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val modules: ArrayList<ModuleEntity> = arrayListOf()

    internal fun setModules(modules: List<ModuleEntity>?) {
        if (modules == null) return
        this.modules.clear()
        this.modules.addAll(modules)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            return ModuleViewHolderHide(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.items_module_list_disable, parent, false)
            )
        } else {
            return ModuleViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.items_module_list_custom, parent, false)
            )
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val module = modules[position]
        if (viewHolder.itemViewType == 0) {
            val moduleViewHolderHide = viewHolder as ModuleViewHolderHide
            moduleViewHolderHide.bind(module.title)
        } else {
            val moduleViewHolder = viewHolder as ModuleViewHolder
            moduleViewHolder.bind(module.title)
            moduleViewHolder.itemView.setOnClickListener { v ->
                listener.onItemClicked(
                    viewHolder.getAdapterPosition(),
                    modules[moduleViewHolder.adapterPosition].moduleId
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        val modulePosition = modules[position].position
        return when {
            modulePosition == 0 -> 1
            modules[modulePosition - 1].isRead -> 1
            else -> 0
        }
    }

    override fun getItemCount(): Int {
        return modules.size
    }

    class ModuleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.text_module_title)
        val textLastSeen: TextView = itemView.findViewById(R.id.text_last_seen)

        fun bind(title: String?) {
            textTitle.text = title
        }
    }

    class ModuleViewHolderHide(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle = itemView.findViewById<TextView>(R.id.text_module_title)

        fun bind(title: String?) {
            textTitle.text = title
        }
    }

}

internal interface MyAdapterClickListener {
    fun onItemClicked(position: Int, moduleId: String?)
}