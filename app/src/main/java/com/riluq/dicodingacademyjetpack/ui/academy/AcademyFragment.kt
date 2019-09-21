package com.riluq.dicodingacademyjetpack.ui.academy


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.riluq.dicodingacademyjetpack.R
import com.riluq.dicodingacademyjetpack.viewmodel.ViewModelFactory
import com.riluq.dicodingacademyjetpack.vo.Status


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class AcademyFragment : Fragment() {

    private var viewModel: AcademyViewModel? = null

    private var rvCourse: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var academyAdapter: AcademyAdapter? = null

    companion object {
        fun newInstance(): Fragment {
            return AcademyFragment()
        }

        private fun obtainViewModel(activity: FragmentActivity?): AcademyViewModel {
            // Use a Factory to inject dependencies into the ViewModel
            val factory = ViewModelFactory.getInstance(activity?.application!!)
            return ViewModelProviders.of(activity, factory).get(AcademyViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_academy, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvCourse = view.findViewById(R.id.rv_academy)
        progressBar = view.findViewById(R.id.progress_bar)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            progressBar?.visibility = View.VISIBLE
            viewModel = obtainViewModel(activity)

            academyAdapter = activity?.let { AcademyAdapter(it) }

            viewModel?.setUsername("Dicoding")
            viewModel?.courses?.observe(this, Observer { courses ->
                if (courses != null) {
                    when (courses.status) {
                        Status.LOADING -> {
                            progressBar?.visibility = View.VISIBLE
                        }
                        Status.SUCCESS -> {
                            progressBar?.visibility = View.GONE
                            academyAdapter?.setListCourses(courses.data)
                            academyAdapter?.notifyDataSetChanged()
                        }
                        Status.ERROR -> {
                            progressBar?.visibility = View.GONE
                            Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            rvCourse?.layoutManager = LinearLayoutManager(context)
            rvCourse?.setHasFixedSize(true)
            rvCourse?.adapter = academyAdapter
        }
    }
}
