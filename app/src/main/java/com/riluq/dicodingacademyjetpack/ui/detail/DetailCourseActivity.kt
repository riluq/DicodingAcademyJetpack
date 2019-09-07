package com.riluq.dicodingacademyjetpack.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.riluq.dicodingacademyjetpack.R
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import com.riluq.dicodingacademyjetpack.ui.reader.CourseReaderActivity
import com.riluq.dicodingacademyjetpack.utils.GlideApp
import com.riluq.dicodingacademyjetpack.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail_course.*


class DetailCourseActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_COURSE = "EXTRA_COURSE"

        private fun obtainViewModel(activity: AppCompatActivity): DetailCourseViewModel {
            // Use a Factory to inject dependencies into the ViewModel
            val factory = ViewModelFactory.getInstance(activity.application)
            return ViewModelProviders.of(activity, factory).get(DetailCourseViewModel::class.java)
        }
    }
    private var viewModel: DetailCourseViewModel? = null

    private lateinit var btnStart: Button
    private lateinit var textTitle: TextView
    private lateinit var textDesc: TextView
    private lateinit var textDate: TextView
    private lateinit var rvModule: RecyclerView
    private lateinit var adapter: DetailCourseAdapter
    private lateinit var imagePoster: ImageView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_course)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = obtainViewModel(this)

        adapter = DetailCourseAdapter()

        progressBar = findViewById(R.id.progress_bar)
        btnStart = findViewById(R.id.btn_start)
        textTitle = findViewById(R.id.text_title)
        textDesc = findViewById(R.id.text_description)
        textDate = findViewById(R.id.text_date)
        rvModule = findViewById(R.id.rv_module)
        imagePoster = findViewById(R.id.image_poster)

        val extras = intent.extras
        if (extras != null) {
            val courseId = extras.getString(EXTRA_COURSE)
            if (courseId != null) {
                progressBar.visibility = View.VISIBLE
                viewModel?.setCourseId(courseId)
            }
        }

        viewModel?.getModules()?.observe(this, Observer {moduleEntities ->
            progressBar.visibility = View.GONE
            adapter.setModules(moduleEntities)
            adapter.notifyDataSetChanged()
        })

        viewModel?.getCourse()?.observe(this, Observer { courseEntity ->
            if (courseEntity != null) {
                populateCourse(courseEntity)
            }
        })

        rvModule.isNestedScrollingEnabled = false
        rvModule.layoutManager = LinearLayoutManager(this)
        rvModule.setHasFixedSize(true)
        rvModule.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(rvModule.context, DividerItemDecoration.VERTICAL)
        rvModule.addItemDecoration(dividerItemDecoration)
    }

    private fun populateCourse(courseEntity: CourseEntity?) {
        textTitle.text = courseEntity?.title
        textDesc.text = courseEntity?.description
        textDate.text = String.format("Deadline %s", courseEntity?.deadline)

        GlideApp.with(applicationContext)
            .load(courseEntity?.imagePath)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
            .into(imagePoster)

        btnStart.setOnClickListener { v ->
            val intent = Intent(this@DetailCourseActivity, CourseReaderActivity::class.java)
            intent.putExtra(CourseReaderActivity.EXTRA_COURSE_ID, viewModel?.getCourseId())
            v.context.startActivity(intent)
        }
    }

}
