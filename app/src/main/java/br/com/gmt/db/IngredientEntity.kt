package br.com.gmt.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.gmt.data.IngredientList

@Entity(tableName = "ingredient_table")
class IngredientEntity(

    @ColumnInfo(name = "name") val name: String,

)
{
    //    @PrimaryKey(autoGenerate = true) val id: Int = 0
    @PrimaryKey var id: Int = 0

    init {
        if (IngredientList.allIngredients().isEmpty()) id = 0
        else {
            if (IngredientList.toInsert.isEmpty())
                id = IngredientList.dbList[IngredientList.dbList.size-1].id + 1
            else
                id = IngredientList.toInsert[IngredientList.toInsert.size-1].id + 1
        }
    }
}