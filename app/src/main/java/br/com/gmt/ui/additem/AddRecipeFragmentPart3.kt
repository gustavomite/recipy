package br.com.gmt.ui.additem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import br.com.gmt.R
import br.com.gmt.data.IngredientList
import br.com.gmt.ui.activity.AddRecipeActivity
import br.com.gmt.ui.viewmodel.AddRecipeViewModel
import com.google.android.material.button.MaterialButton

class AddRecipeFragmentPart3 : Fragment() {
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
        val layoutView = inflater.inflate(R.layout.fragment_add_recipe_part3, container, false)

        val buttonConfirm = layoutView.findViewById<MaterialButton>(R.id.confirmButton)
        val descriptionEdit = layoutView.findViewById<EditText>(R.id.editDescription)
        buttonConfirm.setOnClickListener {
            IngredientList.confirmInsert = true
            viewModel.setDescription(descriptionEdit.text.toString())
        }

        return layoutView
    }
}