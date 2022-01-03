package br.com.gmt.Util

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.preference.PreferenceManager
import br.com.gmt.RecipyApp
import br.com.gmt.Util.Func.startActivity
import br.com.gmt.ui.additem.AddRecipeActivity

object Func {
    fun Activity.startActivity(activity: AppCompatActivity, bundle: Bundle = Bundle(), flags: Int = Intent.FLAG_ACTIVITY_NEW_TASK) =
        startActivity(Intent(this@startActivity, activity.javaClass).apply { putExtras(bundle); addFlags(flags) })

    fun Activity.startActivityTransition(activity: AppCompatActivity, view: View, profile: String, bundle: Bundle = Bundle()) =
        run {
            val intent = Intent(this@startActivityTransition, activity::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this@startActivityTransition,
                view, profile
            )
            intent.putExtra("bundle", bundle)

            val bundleWithOptions = options.toBundle()
            startActivity(
                intent,
                bundleWithOptions
            )
        }

    fun getPrefs() = PreferenceManager.getDefaultSharedPreferences(RecipyApp.context)
}