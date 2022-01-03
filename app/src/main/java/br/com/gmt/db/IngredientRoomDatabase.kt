package br.com.gmt.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(IngredientEntity::class), version = 1, exportSchema = false)
abstract class IngredientRoomDatabase : RoomDatabase() {

    abstract fun ingredientDao(): IngredientDao

    private class IngredientDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.ingredientDao())
                }
            }
        }

        suspend fun populateDatabase(ingredientDao: IngredientDao) {
//            // Delete all content here.
//            ingredientDao.deleteAll()
//
////            // Add sample words.
//            var ingredientEntity = IngredientEntity("hello")
//            ingredientDao.insert(ingredientEntity)
////            recipeEntity = RecipeEntity("World!")
////            recipeDao.insert(recipeEntity)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: IngredientRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): IngredientRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    IngredientRoomDatabase::class.java,
                    "recipes_database"
                )
                    .addCallback(IngredientDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}