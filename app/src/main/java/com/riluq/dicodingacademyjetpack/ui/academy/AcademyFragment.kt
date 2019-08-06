package com.riluq.dicodingacademyjetpack.ui.academy


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.riluq.dicodingacademyjetpack.utils.generateDummyCourses


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class AcademyFragment : Fragment() {

    private var rvCourse: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var academyAdapter: AcademyAdapter? = null

    companion object {
        fun newInstance(): Fragment {
            return AcademyFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.riluq.dicodingacademyjetpack.R.layout.fragment_academy, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvCourse = view.findViewById(com.riluq.dicodingacademyjetpack.R.id.rv_academy)
        progressBar = view.findViewById(com.riluq.dicodingacademyjetpack.R.id.progress_bar)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            academyAdapter = activity?.let { AcademyAdapter(it) }
            academyAdapter?.setListCourses(generateDummyCourses())

            rvCourse?.layoutManager = LinearLayoutManager(context)
            rvCourse?.setHasFixedSize(true)
            rvCourse?.adapter = academyAdapter
        }
    }
}
