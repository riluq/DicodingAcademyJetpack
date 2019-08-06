package com.riluq.dicodingacademyjetpack.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.riluq.dicodingacademyjetpack.ui.academy.AcademyFragment
import com.riluq.dicodingacademyjetpack.ui.bookmark.BookmarkFragment


class HomeActivity : AppCompatActivity() {

    var fragment: Fragment? = null
    private val SELECTED_MENU = "selected_menu"
    private lateinit var navView: BottomNavigationView

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            com.riluq.dicodingacademyjetpack.R.id.action_home -> {

                fragment = AcademyFragment.newInstance()
//                return@OnNavigationItemSelectedListener true
            }
            com.riluq.dicodingacademyjetpack.R.id.action_bookmark -> {

                fragment = BookmarkFragment.newInstance()
//                return@OnNavigationItemSelectedListener true
            }

        }
        fragment.let {
            if (it != null) {
                supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(com.riluq.dicodingacademyjetpack.R.id.container, it)
                    .commit()
            }
        }

        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.riluq.dicodingacademyjetpack.R.layout.activity_home)
        val navView: BottomNavigationView = findViewById(com.riluq.dicodingacademyjetpack.R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        if (savedInstanceState != null) {
            savedInstanceState.getInt(SELECTED_MENU)
        } else {
            navView.selectedItemId = com.riluq.dicodingacademyjetpack.R.id.action_home
        }

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(SELECTED_MENU, navView.selectedItemId)
    }
}
