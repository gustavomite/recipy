package br.com.gmt.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import br.com.gmt.R
import br.com.gmt.data.IngredientList
import br.com.gmt.util.NavigateControl
import br.com.gmt.data.Recipe
import br.com.gmt.ui.viewmodel.AddRecipeViewModel

class AddRecipeActivity : AppCompatActivity() {
    var recipe = Recipe("")
    lateinit var viewModel: AddRecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        // get the view model - needs to be before setContentView to handle screen rotation
        viewModel = ViewModelProvider(this)[AddRecipeViewModel::class.java]

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        // prepare navigation between fragments
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.add_recipe_fragment_view) as NavHostFragment
        NavigateControl.navController = navHostFragment.navController

        // observe if recipe is confirmed
        viewModel.done.observe(this, {
            if (it == true) {
                finishAddRecipe()
            }
        })
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