package com.riluq.dicodingacademyjetpack.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.riluq.dicodingacademyjetpack.R
import com.riluq.dicodingacademyjetpack.ui.academy.AcademyFragment
import com.riluq.dicodingacademyjetpack.ui.bookmark.BookmarkFragment
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    var fragment: Fragment? = null
    private val SELECTED_MENU = "selected_menu"

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_home -> {
                fragment = AcademyFragment.newInstance()
//                return@OnNavigationItemSelectedListener true
            }
            R.id.action_bookmark -> {
                fragment = BookmarkFragment.newInstance()
//                return@OnNavigationItemSelectedListener true
            }

        }
        fragment.let {
            if (it != null) {
                supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.container, it)
                    .commit()
            }
        }

        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        if (savedInstanceState != null) {
            savedInstanceState.getInt(SELECTED_MENU)
        } else {
            nav_view.selectedItemId = R.id.action_home
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_MENU, nav_view.selectedItemId)
    }
}
