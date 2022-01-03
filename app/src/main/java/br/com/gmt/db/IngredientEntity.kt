package br.com.gmt.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredient_table")
class IngredientEntity(

    @ColumnInfo(name = "name") val name: String,
//    @PrimaryKey(autoGenerate = true) val id: Int = 0
    @PrimaryKey val id: Int
) {
}