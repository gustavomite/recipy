package br.com.gmt.ui.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.gmt.ui.main.MainActivity
import br.com.gmt.R
import br.com.gmt.data.Recipe

/**
     * Adapter for the [RecyclerView] in [MainActivity]. Displays [Recipe] data object.
     */
    class RecipeAdapterWithClickListener(
        private val context: Context,
        private val dataset: ArrayList<Recipe>,
        private val clickCallback: OnClickCallback
    ) : RecyclerView.Adapter<RecipeAdapterWithClickListener.ViewHolder>() {
        /**
         * Create new views (invoked by the layout manager)
         */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            // create a new view
            val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_recipe_item, parent, false)

            return ViewHolder(adapterLayout, clickCallback)
        }

        /**
         * Replace the contents of a view (invoked by the layout manager)
         */
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = dataset[position]
            holder.textTitle.text = item.name
            holder.textTime.text = String.format(context.resources.getString(R.string.main_text_list_minutes), item.time)
            holder.recipe = item
        }

        /**
         * Return the size of your dataset (invoked by the layout manager)
         */
        override fun getItemCount() = dataset.size

        interface OnClickCallback {
            fun onClick(data: Recipe)
            fun onLongClick(data: Recipe)
        }

        class ViewHolder(val parentView: View, clickCallback: OnClickCallback) : RecyclerView.ViewHolder(
            parentView
        ) {
            lateinit var recipe: Recipe
            val textTitle: TextView = parentView.findViewById<View>(R.id.ingredient_title) as TextView
            val textTime: TextView = parentView.findViewById<View>(R.id.ingredient_qty) as TextView

            init {
                parentView.setOnClickListener {
                    clickCallback.onClick(recipe)
                }

                parentView.setOnLongClickListener {

                    clickCallback.onLongClick(recipe)
                    return@setOnLongClickListener true
                }
            }
        }
    }