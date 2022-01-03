package br.com.gmt.ui.additem

import android.app.Application
import androidx.lifecycle.*
import br.com.gmt.data.IngredientList
import br.com.gmt.data.Recipe
import br.com.gmt.data.RecipeList
import br.com.gmt.db.IngredientEntity
import br.com.gmt.db.IngredientRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AddRecipeViewModel : ViewModel() {
    private var recipe = Recipe("")
    public var ingredientList = mutableMapOf<Int, String>()

    private val _done = MutableLiveData(false)
    val done: LiveData<Boolean>
        get() = _done

    fun addIngredient(ingr: String, qty: String) {
        // find the ingredient key from the entity
        var id = IngredientList.getId(ingr)
        if (id == -1) {
            id = IngredientList.newIngredient(ingr)
        }
        ingredientList[id] = qty
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

    fun addIngredientFinish() {
        ingredientList.forEach { ingr ->
            recipe.ingredient[ingr.key] = ingr.value
        }
    }

    fun getIngredientsFromDB(): List<String> {
        val listRet = mutableListOf<String>()
        IngredientList.allIngredients().forEach {
            listRet.add(it.name)
        }
        return listRet
    }
}