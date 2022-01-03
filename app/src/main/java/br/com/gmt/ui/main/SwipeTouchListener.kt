package br.com.gmt.ui.main

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.GestureDetector.SimpleOnGestureListener

open class SwipeTouchListener(context: Context?): View.OnTouchListener {

    private var gestureDetector: GestureDetector? = null

    init {
        gestureDetector = GestureDetector(context, GestureListener(::onSwipeRight, ::onSwipeLeft))
    }

    open fun onSwipeLeft() {}

    fun onSwipeRight() {}

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return gestureDetector!!.onTouchEvent(event)
    }

    private class GestureListener(val onSwipeRight: () -> Unit, val onSwipeLeft: () -> Unit) : SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val distanceX = e2.x - e1.x
            val distanceY = e2.y - e1.y
            if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(
                    velocityX
                ) > SWIPE_VELOCITY_THRESHOLD
            ) {
                if (distanceX > 0) onSwipeRight() else onSwipeLeft()
                return true
            }
            return false
        }

        companion object {
            private const val SWIPE_DISTANCE_THRESHOLD = 100
            private const val SWIPE_VELOCITY_THRESHOLD = 100
        }
    }
}