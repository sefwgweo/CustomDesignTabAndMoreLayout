package com.example.customizeddesigntabandmorelayout.ui

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.customizeddesigntabandmorelayout.R
import com.example.customizeddesigntabandmorelayout.databinding.ActivityDetailBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val fragment = DetailFragment.newInstance()
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
        }
        val binding = DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "詳細"
        initSwipeUI(binding)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 以下は透明なActivityにするための処理(OS11以上)
            window.setBackgroundDrawableResource(android.R.color.transparent)
            lifecycleScope.launch(Dispatchers.IO) {
                // メインスレッドで実行するとANRになるため、IOスレッドで実行
                setTranslucent(true)
            }
        }
    }

    private fun initSwipeUI(binding: ActivityDetailBinding) {
        val param: CoordinatorLayout.LayoutParams = binding.container.layoutParams as CoordinatorLayout.LayoutParams
        param.behavior = BottomSheetBehavior<FrameLayout>()

        BottomSheetBehavior.from(binding.container).apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            isHideable = true
            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            state = BottomSheetBehavior.STATE_HIDDEN
                            finish()
                            overridePendingTransition(0, 0)
                        }

                        BottomSheetBehavior.STATE_HIDDEN -> {
                            return
                        }

                        else -> {
                            // noop
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    val upperThreshold = 0.7f
                    if (state == BottomSheetBehavior.STATE_SETTLING && slideOffset < 1) {
                        if (slideOffset >= upperThreshold) {
                            // 位置が0.7以上の場合は、EXPAND状態にする
                            state = BottomSheetBehavior.STATE_EXPANDED
                        }
                    }
                }
            })
        }
    }
}