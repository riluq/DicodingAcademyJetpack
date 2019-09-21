package com.riluq.dicodingacademyjetpack.ui.reader.content


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.riluq.dicodingacademyjetpack.R
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ContentEntity
import com.riluq.dicodingacademyjetpack.data.source.local.entity.ModuleEntity
import com.riluq.dicodingacademyjetpack.ui.reader.CourseReaderViewModel
import com.riluq.dicodingacademyjetpack.utils.MyButton
import com.riluq.dicodingacademyjetpack.viewmodel.ViewModelFactory
import com.riluq.dicodingacademyjetpack.vo.Status
import kotlinx.android.synthetic.main.fragment_module_content.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ModuleContentFragment() : Fragment() {

    companion object {
        val TAG = ModuleContentFragment::class.java.simpleName

        fun newInstance(): ModuleContentFragment {
            return ModuleContentFragment()
        }

        private fun obtainViewModel(activity: FragmentActivity): CourseReaderViewModel {
            // Use a Factory to inject dependencies into the ViewModel
            val factory = ViewModelFactory.getInstance(activity.application)
            return ViewModelProviders.of(activity, factory).get(CourseReaderViewModel::class.java)
        }
    }

    private var viewModel: CourseReaderViewModel? = null

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnNext: Button
    private lateinit var btnPrev: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_module_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = view.findViewById(R.id.web_view)
        progressBar = view.findViewById(R.id.progress_bar)
        btnNext = view.findViewById(R.id.btn_next)
        btnPrev = view.findViewById(R.id.btn_prev)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            viewModel = obtainViewModel(activity!!)
            progressBar.visibility = View.VISIBLE
            viewModel?.selectedModule?.observe(this, Observer { moduleEntity ->
                if (moduleEntity != null) {
                    when(moduleEntity.status) {
                        Status.LOADING -> progressBar.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            setButtonNextPrevState(moduleEntity.data)
                            progressBar.visibility = View.GONE
                            if (moduleEntity.data?.isRead?.not()!!) {
                                viewModel?.readContent(moduleEntity.data);
                            }

                            if (moduleEntity.data.contentEntity != null) {
                                populateWebView(moduleEntity.data.contentEntity)
                            }
                        }
                        Status.ERROR -> progressBar.visibility = View.GONE
                    }
                }
            })
            btnNext.setOnClickListener { viewModel?.setNextPage() }
            btnPrev.setOnClickListener { viewModel?.setPrevPage() }
        }
    }

    private fun populateWebView(content: ContentEntity?) {
        webView.loadData(content?.content, "text/html", "UTF-8")
    }

    private fun setButtonNextPrevState(module: ModuleEntity?) {
        if (activity != null) {
            if (module?.position == 0) {
                btnPrev.isEnabled = false
                btnNext.isEnabled = true
            } else if (module?.position == viewModel?.getModuleSize()?.minus(1)) {
                btnPrev.isEnabled = true
                btnNext.isEnabled = false
            } else {
                btnPrev.isEnabled = true
                btnNext.isEnabled = true
            }
        }
    }

}
