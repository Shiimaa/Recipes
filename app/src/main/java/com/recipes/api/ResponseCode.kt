package com.recipes.api

import okhttp3.ResponseBody
import retrofit2.Response

object ResponseCode {
    fun checkResponseCode(response: Response<ResponseBody?>, callback: ResponseCodeCallback?) {
        if (callback == null) {
            throw RuntimeException("callback instance is NULL!")
        }
        when (response.code()) {
            200 -> callback.onResponse(response)
            204 -> callback.onSuccess(response)
            else -> callback.onFailure()
        }
    }

    interface ResponseCodeCallback {
        fun onResponse(response: Response<ResponseBody?>?)
        fun onSuccess(response: Response<ResponseBody?>?)
        fun onFailure()
    }
}