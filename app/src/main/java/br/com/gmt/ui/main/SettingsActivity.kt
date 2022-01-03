package br.com.gmt.ui.main

import android.content.res.Resources
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import br.com.gmt.R
import androidx.core.app.NavUtils
import br.com.gmt.RecipyApp
import br.com.gmt.Util.Func.getPrefs
import br.com.gmt.data.RecipeList


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onPause() {
        // if there was a change on sort order, prepare resort

        val key = RecipyApp.context.getString(R.string.text_preference_sort_key)
        val default = RecipyApp.context.resources.getStringArray(R.array.sort_values)[0]
        if (getPrefs().getString(key, default)!!.compareTo(RecipeList.sortOrder) != 0) {
            RecipeList.sortOrder = ""
        }
        super.onPause()
    }

    // code below is necessary to go back when back view is clicked
    /* Add this to the manifest
                <meta-data
                android:name="android.support.PARENT_ACTIVITY"
                android:value=".ui.main.MainActivity" />
     */
    // Add this to the main manifest so that it is not recreated
    // android:launchMode="singleTop"
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                overridePendingTransition(br.com.gmt.R.anim.slide_out_left, br.com.gmt.R.anim.slide_in_right)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(br.com.gmt.R.anim.slide_out_left, br.com.gmt.R.anim.slide_in_right)
    }
}