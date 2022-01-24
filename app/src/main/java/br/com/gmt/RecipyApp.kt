package br.com.gmt

import android.app.Application
import android.content.Context
import br.com.gmt.db.IngredientRepository
import br.com.gmt.db.IngredientRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class RecipyApp : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { IngredientRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { IngredientRepository(database.ingredientDao()) }

    companion object {
        private lateinit var instance: RecipyApp
        val context: Context
            get() = instance
    }

    init {
        instance = this
    }
}