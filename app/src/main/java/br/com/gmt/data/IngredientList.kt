package br.com.gmt.data

import br.com.gmt.R
import br.com.gmt.RecipyApp.Companion.context
import br.com.gmt.db.IngredientEntity

object IngredientList {
    var dbList = mutableListOf<IngredientEntity>()
    val toInsert = mutableListOf<IngredientEntity>()

    fun allIngredients(): List<IngredientEntity> = dbList + toInsert

    fun getId(ingredientName: String): Int {
        var found = dbList.find {
            it.name == ingredientName
        }

        if (found == null) {
            found = toInsert.find {
                it.name == ingredientName
            }
        }

        return found?.id ?: -1
    }

    fun newIngredient(name: String): Int {
        val newId: Int
        if (toInsert.size > 0)
            newId = toInsert[toInsert.size-1].id + 1
        else if (dbList.size > 0)
            newId = dbList[dbList.size-1].id + 1
        else newId = 0

        toInsert.add(IngredientEntity(name, newId))

        return newId
    }

    fun ingredientNameFromId(id: Int): String {
        var found = dbList.find {
            it.id == id
        }

        if (found == null) {
            found = toInsert.find {
                it.id == id
            }
        }

        return found?.name ?: context.getString(R.string.recipe_text_ingredient_not_found)
    }
}