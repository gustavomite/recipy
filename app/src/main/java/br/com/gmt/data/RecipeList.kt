package br.com.gmt.data

import br.com.gmt.R
import br.com.gmt.RecipyApp
import br.com.gmt.Util.Func.getPrefs
import br.com.gmt.data.RecipeList.sortOrder
import java.util.*
import kotlin.collections.ArrayList

class CompareRecipe: Comparator<Recipe> {
    override fun compare(o1: Recipe?, o2: Recipe?): Int {
        if (sortOrder.isNullOrBlank()) RecipeList.getOrder()

        return when(sortOrder) {
            "time_asc" -> o1!!.time - o2!!.time
            "time_desc" -> o2!!.time - o1!!.time
            "name_desc" -> o2!!.name.compareTo(o1!!.name)
            else -> o1!!.name.compareTo(o2!!.name)
        }
    }

}
object RecipeList : ArrayList<Recipe>() {
    var sortOrder = ""

    fun getOrder() {
        val key = RecipyApp.context.getString(R.string.text_preference_sort_key)
        val default = RecipyApp.context.resources.getStringArray(R.array.sort_values)[0]
        sortOrder = getPrefs().getString(
            key,
            default
        ).toString()
    }

    override fun add(element: Recipe): Boolean {
        super.add(element)
        Collections.sort(this, CompareRecipe())
        return true
    }

    override fun addAll(elements: Collection<Recipe>): Boolean {
        super.addAll(elements)
        Collections.sort(this, CompareRecipe())
        return true
    }

    fun reorder() {
        val tmpArray = ArrayList<Recipe>(RecipeList)
        clear()
        addAll(tmpArray)
    }

    init {
        // get resort order on init
        // prevent main activity to force resort
        getOrder()
    }
}