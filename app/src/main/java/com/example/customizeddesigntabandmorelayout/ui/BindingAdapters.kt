package com.example.customizeddesigntabandmorelayout.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.pow

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("onSnapScrolled")
    fun setPagerSnapScrollListener(
        view: RecyclerView,
        onSnapScrolled: OnSnapPositionChangeListener? = null,
    ) {
        onSnapScrolled ?: return

        val snapListener = object : RecyclerView.OnScrollListener() {
            var snapPosition = RecyclerView.NO_POSITION
            var behavior: Behavior = Behavior.NOTIFY_ON_SCROLL

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (behavior == Behavior.NOTIFY_ON_SCROLL_STATE_IDLE && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    maybeNotifySnapPositionChange(recyclerView)
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (behavior == Behavior.NOTIFY_ON_SCROLL) {
                    maybeNotifySnapPositionChange(recyclerView)
                }
                view.setPadding(80, 0, 80, 0)
                view.post {
                    (0 until view.childCount).forEach { position ->
                        val child = view.getChildAt(position)
                        val childCenterX = (child.left + child.right) / 2
                        val scaleValue = getGaussianScale(
                            view,
                            childCenterX,
                            1f,
                            1f,
                            150.toDouble()
                        )
                        child.scaleX = scaleValue
                        child.scaleY = scaleValue
                    }
                }
            }

            fun maybeNotifySnapPositionChange(recyclerView: RecyclerView) {
                val snapPosition = PagerSnapHelper().findSnapView(recyclerView.layoutManager)?.let {
                    recyclerView.layoutManager?.getPosition(it)
                } ?: RecyclerView.NO_POSITION

                val snapPositionChanged = this.snapPosition != snapPosition
                if (snapPositionChanged) {
                    onSnapScrolled.onSnapPositionChange(snapPosition)
                    this.snapPosition = snapPosition
                }
            }
        }
        view.addOnScrollListener(snapListener)
    }

    private fun getGaussianScale(
        view: RecyclerView,
        childCenterX: Int,
        minScaleOffset: Float,
        scaleFactor: Float,
        spreadFactor: Double,
    ): Float {
        val recyclerCenterX = (view.left + view.right) / 2
        return (Math.E.pow(
            -(childCenterX - recyclerCenterX.toDouble()).pow(2.toDouble()) / (2 * spreadFactor.pow(2.toDouble()))
        ) * scaleFactor * 0.05 + minScaleOffset).toFloat()
    }

    interface OnSnapPositionChangeListener {

        fun onSnapPositionChange(position: Int)
    }

    enum class Behavior {
        NOTIFY_ON_SCROLL,
        NOTIFY_ON_SCROLL_STATE_IDLE
    }
}