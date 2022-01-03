package br.com.gmt.ui.additem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import br.com.gmt.R
import br.com.gmt.RecipyApp
import br.com.gmt.Util.NavigateControl
import br.com.gmt.db.IngredientEntity
import br.com.gmt.db.IngredientRoomDatabase
import br.com.gmt.ui.adaptors.IngredientAdapterWithClickListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar

class AddRecipeFragmentPart2 : Fragment() {
    private lateinit var viewModel: AddRecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as AddRecipeActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layoutView = inflater.inflate(R.layout.fragment_add_recipe_part2, container, false)
        val buttonNext = layoutView.findViewById<MaterialButton>(R.id.buttonDescriptionFragment)
        val recyclerView = layoutView.findViewById<RecyclerView>(R.id.ingredientsRecyclerView)
        val buttonAdd = layoutView.findViewById<MaterialButton>(R.id.buttonAddIngredient)
        val buttonRemove = layoutView.findViewById<MaterialButton>(R.id.buttonRemoveIngredient)
        val textIngredient = layoutView.findViewById<AutoCompleteTextView>(R.id.editIngredientName)
        val textQty = layoutView.findViewById<EditText>(R.id.editIngredientQty)

        // button next
        buttonNext.setOnClickListener {
            viewModel.addIngredientFinish()
            NavigateControl.navigateUsingDirection(AddRecipeFragmentPart2Directions.actionAddRecipeFragmentPart2ToAddRecipeFragmentPart3())
        }

        // set adapter to ingredient list
        recyclerView.adapter = IngredientAdapterWithClickListener(layoutView.context, viewModel.ingredientList)

        // set adapter to ingredient name
        val items = viewModel.getIngredientsFromDB()
        val adapter = ArrayAdapter(requireContext(), R.layout.menu_ingredient_item, items)
        textIngredient.setAdapter(adapter)

        // new ingredient
        buttonAdd.setOnClickListener {
            val ingr = textIngredient.text.toString()

            if (textIngredient.text.isNullOrBlank() || textQty.text.isNullOrBlank()) {
                Snackbar.make(recyclerView, getString(R.string.add_recipe_text_ingredient_add_failed), Snackbar.LENGTH_SHORT)
                    .setAction(getString(R.string.add_recipe_text_ok)) {}
                    .show()
            } else {

                val size = viewModel.ingredientList.size
                viewModel.addIngredient(ingr, textQty.text.toString())
                textIngredient.requestFocus()

                textIngredient.text.clear()
                textQty.text.clear()

                if (size != viewModel.ingredientList.size) {
                    recyclerView.adapter!!.notifyItemInserted(size)
                }
            }
        }

        // remove ingredient
        buttonRemove.setOnClickListener {
            val removed = (recyclerView.adapter as IngredientAdapterWithClickListener).removeSelected()

            if (removed == 0) {
                Snackbar.make(recyclerView, getString(R.string.add_recipe_text_ingredient_remove_failed), Snackbar.LENGTH_SHORT)
                    .setAction(getString(R.string.add_recipe_text_ok)) {}
                    .show()
            } else {
                recyclerView.adapter!!.notifyDataSetChanged()
            }
        }
        return layoutView
    }
}