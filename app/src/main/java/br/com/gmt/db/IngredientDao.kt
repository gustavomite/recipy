package br.com.gmt.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {

    @Query("SELECT * FROM ingredient_table ORDER BY id ASC")
    fun getAlphabetizedIngredients(): Flow<List<IngredientEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ingredientEntity: IngredientEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(ingredients: List<IngredientEntity>)

    @Query("DELETE FROM ingredient_table")
    suspend fun deleteAll()
}