package com.belajarandroid.finalproject.view

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import androidx.fragment.app.Fragment
import com.belajarandroid.finalproject.R
import com.belajarandroid.finalproject.databinding.ActivityLoginBinding
import com.belajarandroid.finalproject.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        initStatusBar()

        loadFragment(HomeFragment())

        binding.navView.setOnItemSelectedListener {
            var fragment: Fragment? = null

            when (it.itemId) {
                R.id.home -> {
                    fragment = HomeFragment()
                }
                R.id.history -> {
                    fragment = HistoryFragment()
                }
                else -> {
                    fragment = ProfileFragment()
                }
            }
            return@setOnItemSelectedListener loadFragment(fragment)
        }
    }

    private fun loadFragment(fragment: Fragment) : Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_container, fragment)
                .commit()
            return true
        }
        return false
    }

    private fun initStatusBar() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.show(WindowInsets.Type.systemBars())
            window?.statusBarColor = Color.TRANSPARENT
        } else
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window?.statusBarColor = Color.TRANSPARENT
    }
}