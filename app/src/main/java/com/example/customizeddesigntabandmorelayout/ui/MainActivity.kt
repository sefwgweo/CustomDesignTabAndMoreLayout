package com.example.customizeddesigntabandmorelayout.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.customizeddesigntabandmorelayout.R
import com.example.customizeddesigntabandmorelayout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val fragment = TabFragment.newInstance()
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
        }
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
}
