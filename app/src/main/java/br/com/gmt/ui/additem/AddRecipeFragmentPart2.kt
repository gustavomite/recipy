package br.com.gmt.ui.additem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import br.com.gmt.R
import br.com.gmt.util.NavigateControl
import br.com.gmt.data.IngredientList
import br.com.gmt.ui.activity.AddRecipeActivity
import br.com.gmt.ui.adapter.IngredientAdapter
import br.com.gmt.ui.viewmodel.AddRecipeViewModel
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
        val autoCompleteTextIngredient = layoutView.findViewById<AutoCompleteTextView>(R.id.editIngredientName)
        val textQty = layoutView.findViewById<EditText>(R.id.editIngredientQty)

        // button next
        buttonNext.setOnClickListener {
            NavigateControl.navigateUsingDirection(AddRecipeFragmentPart2Directions.actionAddRecipeFragmentPart2ToAddRecipeFragmentPart3())
        }

        // set adapter to ingredient list
        recyclerView.adapter = IngredientAdapter(viewModel.recipe)

        // set ingredients list to adapter
        val adapter = ArrayAdapter(requireContext(), R.layout.menu_ingredient_item, IngredientList.getNames())
        autoCompleteTextIngredient.setAdapter(adapter)

        // new ingredient
        buttonAdd.setOnClickListener {
            val ingr = autoCompleteTextIngredient.text.toString()

            if (autoCompleteTextIngredient.text.isNullOrBlank() || textQty.text.isNullOrBlank()) {
                Snackbar.make(recyclerView, getString(R.string.add_recipe_text_ingredient_add_failed), Snackbar.LENGTH_SHORT)
                    .setAction(getString(R.string.add_recipe_text_ok)) {}
                    .show()
            } else {

                val size = viewModel.recipe.ingredientList.size
                viewModel.addIngredient(ingr, textQty.text.toString())
                autoCompleteTextIngredient.requestFocus()

                autoCompleteTextIngredient.text.clear()
                textQty.text.clear()

                if (size != viewModel.recipe.ingredientList.size) {
                    recyclerView.adapter!!.notifyItemInserted(size)
                }
            }
        }

        // remove ingredient
        buttonRemove.setOnClickListener {
            val removed = (recyclerView.adapter as IngredientAdapter).removeSelected()

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