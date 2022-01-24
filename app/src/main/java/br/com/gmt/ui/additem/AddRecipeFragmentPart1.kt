package br.com.gmt.ui.additem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import br.com.gmt.R
import br.com.gmt.util.NavigateControl
import br.com.gmt.ui.activity.AddRecipeActivity
import br.com.gmt.ui.viewmodel.AddRecipeViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar

class AddRecipeFragmentPart1 : Fragment() {

    companion object {
        fun newInstance() = AddRecipeFragmentPart1()
    }

    private lateinit var viewModel: AddRecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val layoutView = inflater.inflate(R.layout.fragment_add_recipe_part1, container, false)
        val slider = layoutView.findViewById<Slider>(R.id.sliderDuration)
        val minutesText = layoutView.findViewById<TextView>(R.id.textViewSelectedTime)

        slider.addOnChangeListener { _, value, _ ->
            minutesText.text = String.format( resources.getString(R.string.main_text_list_minutes), value.toInt().toString())
        }
        slider.value = 5F

        val button = layoutView.findViewById<MaterialButton>(R.id.buttonIngredientsFragment)
        button.setOnClickListener {
            val textView = layoutView.findViewById<EditText>(R.id.textProduct)

            if (textView.text.isNullOrBlank()) {
                Snackbar.make(textView, getString(R.string.add_recipe_text_recipe_name_failed), Snackbar.LENGTH_SHORT)
                    .setAction(getString(R.string.add_recipe_text_ok)) {}
                    .show()
            } else {
                viewModel.setNameTime(textView.text.toString(), slider.value.toInt())
//                NavigateControl.navigateToFrame(R.id.addRecipeFragmentPart2, button, "add_recipe_profile2")
                NavigateControl.navigateUsingDirection(AddRecipeFragmentPart1Directions.actionAddRecipeFragmentPart1ToAddRecipeFragmentPart2())
            }
        }
        return layoutView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as AddRecipeActivity).viewModel

    }
}