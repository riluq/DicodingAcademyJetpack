package com.riluq.dicodingacademyjetpack.ui.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.riluq.dicodingacademyjetpack.R
import com.riluq.dicodingacademyjetpack.data.CourseEntity
import com.riluq.dicodingacademyjetpack.data.ModuleEntity
import com.riluq.dicodingacademyjetpack.ui.reader.CourseReaderActivity
import com.riluq.dicodingacademyjetpack.utils.GlideApp
import kotlinx.android.synthetic.main.activity_detail_course.*


class DetailCourseActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_COURSE = "EXTRA_COURSE"
    }
    private val viewModel: DetailCourseViewModel by lazy {
        ViewModelProviders.of(this).get(DetailCourseViewModel::class.java)
    }

    private var modules: MutableList<ModuleEntity>? = mutableListOf()

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
                viewModel.setCourseId(courseId)
                modules = viewModel.getModules()
                adapter.setModules(modules)
            }
        }
        if (viewModel.getCourse() != null) {
            populateCourse(viewModel.getCourse())
        }

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
            intent.putExtra(CourseReaderActivity.EXTRA_COURSE_ID, viewModel.getCourseId())
            v.context.startActivity(intent)
        }
    }

}
