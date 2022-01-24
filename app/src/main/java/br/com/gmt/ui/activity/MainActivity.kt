package br.com.gmt.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.com.gmt.R
import br.com.gmt.RecipyApp.Companion.context
import br.com.gmt.util.Func.startActivity
import br.com.gmt.data.Recipe
import br.com.gmt.data.RecipeList
import br.com.gmt.ui.adapter.RecipeAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

import android.content.res.Configuration
import androidx.activity.viewModels
import br.com.gmt.RecipyApp
import br.com.gmt.data.IngredientList
import br.com.gmt.util.Func.startActivityTransition
import br.com.gmt.ui.main.*


class MainActivity : AppCompatActivity(), RecipeAdapter.OnClickCallback {
    val viewModel: MainViewModel by viewModels {
        MainActivityViewModelFactory((application as RecipyApp).repository)
    }
    lateinit var recyclerView: RecyclerView

    @SuppressLint("NotifyDataSetChanged", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // adjust screen according to orientation
        onConfigurationChanged(resources.configuration)

        recyclerView = findViewById<RecyclerView>(R.id.recipesRecyclerView)
        recyclerView.adapter = RecipeAdapter(this, RecipeList, this)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)

        // normal way to get viewmodel - will not work because it needs a parameter
//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        if (savedInstanceState == null) {
            viewModel.loadFromNetwork()
        }

        // load ingredients from DB
        viewModel.allIngredients.observe(this, { ingredientList ->
            viewModel.loadIngredientsFromDB(ingredientList)
        })

        // observe network/data handling completion
        viewModel.loadComplete.observe(this, {
            if (it == true) {
                val progressBar = findViewById<ProgressBar>(R.id.progressBar)
                progressBar.visibility = View.GONE

                recyclerView.adapter!!.notifyDataSetChanged()

                checkEmpty()
            }
        })

        // observe network errors
        viewModel.loadError.observe(this, {
            if (it == true) {
                Snackbar.make(this, recyclerView, getText(R.string.network_error), Snackbar.LENGTH_SHORT)
                    .setAction(getString(R.string.add_recipe_text_ok)) {}
                    .show()
            }
        })

        // swipe right to open config
        val constraintLayout = findViewById<ConstraintLayout>(R.id.mainConstraintLayout)
        constraintLayout.setOnTouchListener(object: SwipeTouchListener(context) {
            override fun onSwipeLeft() {
                startActivity(SettingsActivity())
                overridePendingTransition(br.com.gmt.R.anim.slide_out_right, br.com.gmt.R.anim.slide_in_left)
            }
        })

        // new recipe button
        val fabNewRecipe = findViewById<FloatingActionButton>(R.id.newRecipeFloatingActionButton)
        fabNewRecipe.setOnClickListener {
            startActivityTransition(AddRecipeActivity(), fabNewRecipe, getString(R.string.transition_profile_add_recipe))
        }
    }

    fun checkEmpty() {
        // if list is empty, show message to insert new recipe
        val alertView = findViewById<TextView>(R.id.noRecipeTextView)

        if (recyclerView.adapter!!.itemCount == 0)
            alertView.visibility = View.VISIBLE
        else
            alertView.visibility = View.GONE
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        // called when user comes back to main activity
        super.onResume()

        // if new recipe was added, insert ingredients to db
        if (IngredientList.confirmInsert)
            viewModel.insertIngredientsToDB()

        // check if we need to resort our items
        if (viewModel.checkSortOrder()) {
            val progressBar = findViewById<ProgressBar>(R.id.progressBar)
            progressBar.visibility = View.VISIBLE
        }

        recyclerView.adapter!!.notifyDataSetChanged()
    }

    override fun onClick(data: Recipe) {
        // transition using a shared element (defined in the xml)
        val index = RecipeList.indexOf(data)
        val bundle = Bundle()
        bundle.putInt("index", index)
        startActivityTransition(RecipeActivity(), recyclerView, getString(R.string.transition_profile_recipe), bundle)
    }

    override fun onLongClick(data: Recipe) {
        // confirm if user wants to remove a recipe
        val dialog = RemoveRecipeDialogFragment(recyclerView, data)
        dialog.show(supportFragmentManager, "RemoveRecipeDialogFragment")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // orientation changed

        // needs this on manifest
        // android:configChanges="orientation|screenSize"

        // Checks the orientation of the screen
        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            val linearLayout = findViewById<LinearLayout>(R.id.imageLinearLayout)
            linearLayout.visibility = View.GONE
        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            val linearLayout = findViewById<LinearLayout>(R.id.imageLinearLayout)
            linearLayout.visibility = View.VISIBLE
        }
    }
}
