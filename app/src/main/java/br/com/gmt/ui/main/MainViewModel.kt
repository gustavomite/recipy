package br.com.gmt.ui.main

import android.content.Context
import android.widget.TextView
import androidx.lifecycle.*
import br.com.gmt.R
import br.com.gmt.RecipyApp.Companion.context
import br.com.gmt.data.IngredientList
import br.com.gmt.data.Recipe
import br.com.gmt.data.RecipeList
import br.com.gmt.db.IngredientEntity
import br.com.gmt.db.IngredientRepository
import br.com.gmt.network.RecipeApi
import br.com.gmt.network.VolleyApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(var repository: IngredientRepository): ViewModel() {
    private val _loadComplete = MutableLiveData(false)
    val loadComplete: LiveData<Boolean>
        get() = _loadComplete

    private val _loadError = MutableLiveData(false)
    val loadError: LiveData<Boolean>
        get() = _loadError

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allIngredients: LiveData<List<IngredientEntity>> = repository.allIngredients.asLiveData()

    fun loadIngredientsFromDB(list: List<IngredientEntity>) {
//        list.forEach { element ->
//            IngredientList.dbList.add(element)
//        }
        IngredientList.dbList.clear()
        IngredientList.dbList += list
    }

    fun insertIngredientsToDB() {
//        IngredientList.toInsert.forEach {
//            insertIngredient(it)
//            IngredientList.toInsert.remove(it)
//        }
        insertIngredientList(IngredientList.toInsert)
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    private fun insertIngredient(ingredient: IngredientEntity) = viewModelScope.launch {
        repository.insert(ingredient)
    }

    private fun insertIngredientList(ingredients: MutableList<IngredientEntity>) = viewModelScope.launch {
        repository.insertAll(ingredients)
        ingredients.clear()
    }

//    suspend fun loadRecipesVolley(context: Context) {
//        // request the json from api server
//        val vol = VolleyApiService(context, object: VolleyApiService.RequestCallback {
//            override fun onEnd(response: String) {
//                // resp has the json in a string
//            }
//        }).get()
//    }

    suspend fun loadRecipesRetrofit() {
        try {
            val listResult = RecipeApi.retrofitService.getPosts()
            listResult.forEach { it ->
                val rec = Recipe("${context.getString(R.string.recipe_text_recipe)} ${it.name}")
                rec.description = it.description
                val listIngredients = it.listIngredients.split(' ')
                listIngredients.forEach { ingr ->
                    // map random text to ingredient: first letter code represents the ingredient index and length represents the quantity
                    rec.ingredient[ingr[0].toChar().code - 97] = ingr.length.toString()
                }
                rec.time = it.time
                RecipeList.add(rec)
            }
        } catch (e: Exception) {
            _loadError.value = true
        } finally {
            _loadComplete.value = true
        }
    }

    fun checkResort(): Boolean {
        if (RecipeList.sortOrder.isNullOrBlank())
        {
            viewModelScope.launch {
                RecipeList.reorder()
                // postvalue is used to make sure it is called after the caller of this function already showed the progress bar
                // using value = true has an immediate effect and will cause the progressbar to remain in place
                _loadComplete.postValue(true)
            }
            return true
        }

        return false
    }

    fun loadFromNetwork() {
        CoroutineScope(Dispatchers.Main).launch {
            loadRecipesRetrofit()
//            loadRecipesVolley()
        }
    }
}

class MainActivityViewModelFactory(private val repository: IngredientRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}