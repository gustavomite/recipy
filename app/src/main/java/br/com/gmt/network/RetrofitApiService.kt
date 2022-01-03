package br.com.gmt.network

import br.com.gmt.data.RecipeJSON
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
//import retrofit2.converter.scalars.ScalarsConverterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.http.GET

private val BASE_URL =
    "https://jsonplaceholder.typicode.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
//    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()



interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<RecipeJSON>
}

object RecipeApi {
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
