package com.riluq.dicodingacademyjetpack.ui.reader

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ModuleEntity
import com.riluq.dicodingacademyjetpack.ui.reader.content.ModuleContentFragment
import com.riluq.dicodingacademyjetpack.ui.reader.list.ModuleListFragment
import com.riluq.dicodingacademyjetpack.viewmodel.ViewModelFactory
import com.riluq.dicodingacademyjetpack.vo.Resource
import com.riluq.dicodingacademyjetpack.vo.Status
import com.riluq.dicodingacademyjetpack.R


class CourseReaderActivity : AppCompatActivity(), CourseReaderCallback {

    companion object {
        const val EXTRA_COURSE_ID = "extra_course_id"

        private fun obtainViewModel(activity: FragmentActivity): CourseReaderViewModel {
            // Use a Factory to inject dependencies into the ViewModel
            val factory = ViewModelFactory.getInstance(activity.application)
            return ViewModelProviders.of(activity, factory).get(CourseReaderViewModel::class.java)
        }
    }

    private var isLarge: Boolean = false

    private var viewModel: CourseReaderViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_reader)
        if (findViewById<FrameLayout>(R.id.frame_list) != null) {
            isLarge = true
        }
        viewModel = obtainViewModel(this)

        viewModel?.modules?.observe(this, initObserver())

        val bundle = intent.extras
        if (bundle != null) {
            val courseId = bundle.getString(EXTRA_COURSE_ID)
            if (courseId != null) {
                viewModel!!.setCourseId(courseId)
                populateFragment()
            }
        }
    }

    override fun moveTo(position: Int, moduleId: String) {
        if (isLarge.not()) {
            val fragment = ModuleContentFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.frame_container, fragment, ModuleContentFragment.TAG)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount <= 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    private fun removeObserver() {
        viewModel?.modules?.removeObserver(initObserver())
    }

    private fun initObserver() = Observer<Resource<List<ModuleEntity>>> { modules ->
        if (modules != null) {
            when (modules.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    if (modules.data != null && modules.data.isNotEmpty()) {
                        val firstModule = modules.data[0]
                        val isFirstModuleRead = firstModule.isRead

                        if (isFirstModuleRead.not()) {
                            val firstModuleId = firstModule.moduleId
                            viewModel?.setSelectedModule(firstModuleId)
                        } else {
                            val lastReadModuleId = getLastModuleFromModules(modules.data)
                            if (lastReadModuleId != null) {
                                viewModel?.setSelectedModule(lastReadModuleId)
                            }
                        }
                    }
                    removeObserver()
                }
                Status.ERROR -> {
                    Toast.makeText(this, "Gagal menampilkan data.", Toast.LENGTH_SHORT).show()
                    removeObserver()
                }
            }
        }
    }

    private fun getLastModuleFromModules(moduleEntities: List<ModuleEntity>): String? {
        var lastReadModule: String? = null
        for (moduleEntity in moduleEntities) {
            if (moduleEntity.isRead) {
                lastReadModule = moduleEntity.moduleId
                continue
            }
            break
        }
        return lastReadModule
    }

    private fun populateFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (isLarge.not()) {
            var fragment = supportFragmentManager.findFragmentByTag(ModuleListFragment.TAG)
            if (fragment == null) {
                fragment = ModuleListFragment.newInstance()
                fragmentTransaction.add(R.id.frame_container, fragment, ModuleListFragment.TAG)
                fragmentTransaction.addToBackStack(null)
            }
            fragmentTransaction.commit()
        } else {
            var fragmentList = supportFragmentManager.findFragmentByTag(ModuleListFragment.TAG)

            if (fragmentList == null) {
                fragmentList = ModuleListFragment.newInstance()
                fragmentTransaction.add(R.id.frame_list, fragmentList, ModuleListFragment.TAG)
            }

            var fragmentContent = supportFragmentManager.findFragmentByTag(ModuleContentFragment.TAG)
            if (fragmentContent == null) {
                fragmentContent = ModuleContentFragment.newInstance()
                fragmentTransaction.add(R.id.frame_content, fragmentContent, ModuleContentFragment.TAG)
            }
            fragmentTransaction.commit()
        }
    }
}