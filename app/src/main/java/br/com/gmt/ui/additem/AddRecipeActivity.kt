package br.com.gmt.ui.additem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import br.com.gmt.R
import br.com.gmt.RecipyApp
import br.com.gmt.Util.NavigateControl
import br.com.gmt.data.Recipe
import br.com.gmt.ui.main.MainViewModel

class AddRecipeActivity : AppCompatActivity() {
    var recipe = Recipe("")
    lateinit var viewModel: AddRecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)
        if (savedInstanceState == null) {
//            original code
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, AddRecipeFragmentPart1.newInstance())
//                .commitNow()

                // get the view model
            viewModel = ViewModelProvider(this)[AddRecipeViewModel::class.java]

            val navHostFragment = supportFragmentManager.findFragmentById(R.id.add_recipe_fragment_view) as NavHostFragment
            NavigateControl.navController = navHostFragment.navController

            viewModel.done.observe(this, {
                if (it == true) {
                    finishAddRecipe()
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun finishAddRecipe() {
        // finish with transition
        supportFinishAfterTransition()
    }

}