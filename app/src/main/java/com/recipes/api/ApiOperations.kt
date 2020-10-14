package com.recipes.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

object ApiOperations {
    private val apiInterface: ApiInterface = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface::class.java)

    fun retrieveArticlesList(callback: Callback<ResponseBody>?) {
        val call: Call<ResponseBody> = apiInterface.getRecipesList()
        call.enqueue(callback)
    }

    fun downloadArticleImage(url: String?, callback: Callback<ResponseBody>?) {
        val call: Call<ResponseBody> = apiInterface.downloadImage(url)
        call.enqueue(callback)
    }

}