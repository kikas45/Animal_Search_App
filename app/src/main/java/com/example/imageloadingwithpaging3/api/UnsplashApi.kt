package com.example.imageloadingwithpaging3.api

import com.example.imageloadingwithpaging3.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {
    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
        const val CLIENT_ID = BuildConfig.UNSPLASH_ACCESS_KEY   // this is proper way to hide your documentation

    }

    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID") // this  header is required by the Api , see more in documentation
    @GET("search/photos")
    suspend fun searchPhotos(
        // this are the queries per required by the documentation
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UnsplashResponse

}
