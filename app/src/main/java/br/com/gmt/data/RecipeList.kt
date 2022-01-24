package br.com.gmt.data

import br.com.gmt.R
import br.com.gmt.RecipyApp
import br.com.gmt.util.Func.getPrefs
import br.com.gmt.data.RecipeList.sortOrder
import java.util.*
import kotlin.collections.ArrayList

class CompareRecipe: Comparator<Recipe> {
    override fun compare(o1: Recipe?, o2: Recipe?): Int {
        return when (sortOrder) {
            "time_asc" -> o1!!.time - o2!!.time
            "time_desc" -> o2!!.time - o1!!.time
            "name_desc" -> o2!!.name.compareTo(o1!!.name)
            else -> o1!!.name.compareTo(o2!!.name)
        }
    }

}
object RecipeList : ArrayList<Recipe>() {
    // lastSortOrder starts with default order
    var lastSortOrder = RecipyApp.context.resources.getStringArray(R.array.sort_values)[0]

    // sortOrder always gets current sort order for the configuration
    val sortOrder : String
        get() = getPrefs().getString(
            RecipyApp.context.resources.getString(R.string.text_preference_sort_key),
            RecipyApp.context.resources.getStringArray(R.array.sort_values)[0]).toString()

    override fun add(element: Recipe): Boolean {
        // add element and put it in the correct position
        super.add(element)
        Collections.sort(this, CompareRecipe())
        return true
    }

    override fun addAll(elements: Collection<Recipe>): Boolean {
        // add list of elements and put them in the correct position
        super.addAll(elements)
        Collections.sort(this, CompareRecipe())
        return true
    }

    fun reorder() {
        // reorder the whole list
        val tmpArray = ArrayList<Recipe>(this)
        clear()
        addAll(tmpArray)
        lastSortOrder = sortOrder
    }

    fun checkSortOrder(): Boolean {
        // checks if there's a change in the ordering set
        // if true reorder() needs to be called
        return sortOrder.compareTo(lastSortOrder) != 0
    }
}