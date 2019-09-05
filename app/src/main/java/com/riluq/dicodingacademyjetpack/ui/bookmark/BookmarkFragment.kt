package com.riluq.dicodingacademyjetpack.ui.bookmark


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.riluq.dicodingacademyjetpack.data.source.local.entity.CourseEntity
import androidx.core.app.ShareCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.riluq.dicodingacademyjetpack.R
import com.riluq.dicodingacademyjetpack.ui.academy.AcademyViewModel
import com.riluq.dicodingacademyjetpack.viewmodel.ViewModelFactory


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class BookmarkFragment : Fragment(), BookmarkFragmentCallback {


    private var viewModel: BookmarkViewModel? = null
    private var courses: List<CourseEntity> = listOf()

    private lateinit var adapter: BookmarkAdapter
    private lateinit var rvBookmark: RecyclerView
    private lateinit var progressBar: ProgressBar

    companion object {
        fun newInstance(): Fragment {
            return BookmarkFragment()
        }
        private fun obtainViewModel(activity: FragmentActivity?): BookmarkViewModel {
            // Use a Factory to inject dependencies into the ViewModel
            val factory = ViewModelFactory.getInstance(activity?.application!!)
            return ViewModelProviders.of(activity, factory).get(BookmarkViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvBookmark = view.findViewById(R.id.rv_bookmark)
        progressBar = view.findViewById(R.id.progress_bar)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            viewModel = obtainViewModel(activity)

            courses = viewModel!!.getBookmarks()

            adapter = BookmarkAdapter(activity!!, this)
            adapter.setListCourses(courses)

            rvBookmark.layoutManager = LinearLayoutManager(context)
            rvBookmark.setHasFixedSize(true)
            rvBookmark.adapter = adapter
        }
    }

    override fun onShareClick(course: CourseEntity) {
        if (activity != null) {
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                .from(activity!!)
                .setType(mimeType)
                .setChooserTitle("Bagikan aplikasi ini sekarang.")
                .setText(String.format("Segera daftar kelas %s di dicoding.com", course.title))
                .startChooser()
        }
    }

}
