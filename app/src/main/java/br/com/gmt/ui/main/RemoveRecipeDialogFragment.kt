package br.com.gmt.ui.main

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import br.com.gmt.R
import br.com.gmt.data.Recipe
import br.com.gmt.data.RecipeList

class RemoveRecipeDialogFragment(var recyclerView: RecyclerView, var data: Recipe) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.dialog_remove_recipe)
                .setPositiveButton(
                    R.string.dialog_remove_recipe_confirm,
                    DialogInterface.OnClickListener { dialog, id ->
                        RecipeList.remove(data)
                        recyclerView.adapter!!.notifyDataSetChanged()
                    })
                .setNegativeButton(R.string.dialog_remove_recipe_cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}