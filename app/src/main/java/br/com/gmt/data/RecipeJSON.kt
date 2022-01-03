package br.com.gmt.data

import com.squareup.moshi.Json

data class RecipeJSON(
    @Json(name = "userId") val time: Int,
    @Json(name = "id") val name: String,
    @Json(name = "title") val listIngredients: String,
    @Json(name = "body") val description: String)