package br.com.gmt.util

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.IdRes
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDirections

object NavigateControl {
    lateinit var navController: NavController

    fun navigateToFrame(@IdRes destinationId: Int, view: View? = null, profile: String? = null) {
        Handler(Looper.getMainLooper()).post {
            val onStack = navController.let { isFrameAlreadyOnStack(it, destinationId) }

            // if frame not on stack or different than current, advance
            if (onStack != navController.currentBackStackEntry) {
                // keep it as the only frame on stack by removing anything already there
//                navController?.popBackStack()

//                if (view != null && profile != null) {
//                    val extras = FragmentNavigatorExtras(view to profile)
//                    navController.navigate(destinationId, null, null, extras)
//                } else
                    navController.navigate(destinationId)
            }
        }


    }

    fun navigateUsingDirection(action: NavDirections, view: View? = null, profile: String? = null) {
        Handler(Looper.getMainLooper()).post {
                navController.navigate(action)
        }


    }

    private fun isFrameAlreadyOnStack(
        navController: NavController,
        @IdRes destinationId: Int
    ): NavBackStackEntry? {
        var frame: NavBackStackEntry?
        try {
            frame = navController.getBackStackEntry(destinationId)
        } catch (e: IllegalArgumentException) {
            frame = null
        }
        return frame
    }
}