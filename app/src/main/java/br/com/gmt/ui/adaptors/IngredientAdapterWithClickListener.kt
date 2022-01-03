package br.com.gmt.ui.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.gmt.R
import br.com.gmt.data.IngredientList
import br.com.gmt.data.Recipe

class IngredientAdapterWithClickListener(
    private val context: Context,
    private val dataset: MutableMap<Int, String>
    ) : RecyclerView.Adapter<IngredientAdapterWithClickListener.ViewHolder>() {
        val selected = mutableMapOf<Int, Boolean>()

        /**
         * Create new views (invoked by the layout manager)
         */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            // create a new view
            val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_ingredient_item, parent, false)

            return ViewHolder(adapterLayout, selected)
        }

        /**
         * Replace the contents of a view (invoked by the layout manager)
         */
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val key: Int = dataset.keys.toList()[position]
            val valueForKey = dataset[key]
            holder.textTitle.text = IngredientList.ingredientNameFromId(key)
            holder.textQty.text = valueForKey.toString()
            holder.checkBox.isChecked = false
        }

        /**
         * Return the size of your dataset (invoked by the layout manager)
         */
        override fun getItemCount() = dataset.size

        fun removeSelected(): Int {
            var count = 0
            selected.forEach {
                if (it.value) {
                    dataset.remove(it.key)
                    count++
                }
            }

            return count
        }

        class ViewHolder(val parentView: View, val selected: MutableMap<Int, Boolean>) : RecyclerView.ViewHolder(
            parentView
        ), View.OnClickListener {
            val textTitle: TextView = parentView.findViewById<View>(R.id.ingredient_title) as TextView
            val textQty: TextView = parentView.findViewById<View>(R.id.ingredient_qty) as TextView
            val checkBox: CheckBox = parentView.findViewById<View>(R.id.checkBox) as CheckBox

            init {
                checkBox.setOnClickListener(this)
                parentView.setOnClickListener(this)
            }
            override fun onClick(view: View?) {
                if (view !is CheckBox)
                    checkBox.isChecked = !checkBox.isChecked
                selected[IngredientList.getId(textTitle.text.toString())] = checkBox.isChecked
            }
        }
    }