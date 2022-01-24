package br.com.gmt.data

import br.com.gmt.R
import br.com.gmt.RecipyApp.Companion.context
import br.com.gmt.db.IngredientEntity

object IngredientList {
    var dbList = mutableListOf<IngredientEntity>()
    val toInsert = mutableListOf<IngredientEntity>()
    var confirmInsert = false

    fun allIngredients(): List<IngredientEntity> = dbList + toInsert

    fun newIngredient(name: String): Int {
        val ingr = allIngredients().filter {
            it.name == name
        }

        if (ingr.isNotEmpty())
            return ingr[0].id
        else {
            val newIngr = IngredientEntity(name)
            toInsert.add(newIngr)
            return newIngr.id
        }
    }

    fun getId(ingredientName: String): Int {
        var found = allIngredients().find {
            it.name == ingredientName
        }

        return found?.id ?: -1
    }

    fun getName(key: Int): String {
        val found = allIngredients().find {
            it.id == key
        }

        return found?.name ?: context.getString(R.string.recipe_text_ingredient_not_found)
    }

    fun remove(id: Int) {
        val ingr = allIngredients().filter {
            it.id == id
        }

        if (ingr.isNotEmpty()) {
            dbList.remove(ingr[0])
            toInsert.remove(ingr[0])
        }
    }

    fun getNames(): List<String> {
        return allIngredients().flatMap { listOf(it.name) }
    }
}