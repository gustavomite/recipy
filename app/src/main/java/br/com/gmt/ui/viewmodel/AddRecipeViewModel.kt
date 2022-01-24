package br.com.gmt.ui.viewmodel

import androidx.lifecycle.*
import br.com.gmt.data.IngredientList
import br.com.gmt.data.Recipe
import br.com.gmt.data.RecipeList

class AddRecipeViewModel : ViewModel() {
    var recipe = Recipe("")

    private val _done = MutableLiveData(false)
    val done: LiveData<Boolean>
        get() = _done

    fun addIngredient(ingr: String, qty: String) {
        // find the ingredient key from the entity
        var id = IngredientList.newIngredient(ingr)
        recipe.ingredientList[id] = qty
    }

    fun setName(name: String) {
        recipe.name = name
    }

    fun setNameTime(name: String, time: Int) {
        setName(name)
        recipe.time = time
    }

    fun setDescription(desc: String) {
        recipe.description = desc
        RecipeList.add(recipe)
        _done.postValue(true)
    }

    fun getIngredientsFromDB(): List<String> {
        val listRet = mutableListOf<String>()
        IngredientList.allIngredients().forEach {
            listRet.add(it.name)
        }
        return listRet
    }

    init {
        IngredientList.confirmInsert = false
    }
}