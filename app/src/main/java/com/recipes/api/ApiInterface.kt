package com.recipes.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiInterface {
    @GET(GET_RECIPES_URL)
    fun getRecipesList(): Call<ResponseBody>

    @GET
    fun downloadImage(
            @Url url: String?
    ): Call<ResponseBody>
}