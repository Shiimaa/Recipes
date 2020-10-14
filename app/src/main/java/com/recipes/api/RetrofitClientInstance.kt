package com.recipes.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientInstance {
    private var retrofit: Retrofit? = null
    private val mutex = Any()

    fun getRetrofitInstance(): Retrofit {
        if (retrofit == null) {
            synchronized(mutex) {
                if (retrofit == null) {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.level = HttpLoggingInterceptor.Level.BODY
                    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
                    retrofit = Retrofit.Builder()
                            .baseUrl("https://hf-android-app.s3-eu-west-1.amazonaws.com")
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client)
                            .build()
                }
            }
        }
        return retrofit!!
    }
}