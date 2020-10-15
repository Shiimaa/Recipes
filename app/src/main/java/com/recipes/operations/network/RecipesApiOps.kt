package com.recipes.operations.network

import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.recipes.api.ApiOperations.retrieveArticlesList
import com.recipes.api.ResponseCode
import com.recipes.api.ResponseCode.checkResponseCode
import com.recipes.application.App
import com.recipes.dispatchqueues.synchronous.postToHandleDbQueue
import com.recipes.dispatchqueues.synchronous.postToHandleNetworkResponseQueue
import com.recipes.dispatchqueues.synchronous.postToNetworkQueue
import com.recipes.operations.db.insertRecipes
import com.recipes.operations.json.parseArticlesResponseBody
import com.recipes.utils.RECIPES_NOT_RETRIEVED
import com.recipes.utils.RECIPES_RETRIEVED_SUCCESSFULLY
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "RecipesApiOps"
fun getRecipes() {
    postToNetworkQueue(Runnable {
        retrieveArticlesList(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response == null) {
                    Log.e(TAG, "[getNewsArticles] response is null -> return")
                    return
                }
                handleNewsArticlesResponse(response)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Log.e(TAG, "[onFailure] ERROR: " + Log.getStackTraceString(t))
            }
        })
    })
}

private fun handleNewsArticlesResponse(response: Response<ResponseBody?>) {
    checkResponseCode(response, object : ResponseCode.ResponseCodeCallback {
        override fun onResponse(response: Response<ResponseBody?>?) {
            postToHandleNetworkResponseQueue(Runnable {
                Log.d(TAG, "[handleNewsArticlesResponse] attempt to parse json object")
                val responseBody = response?.body()
                if (responseBody != null) {
                    val recipes = parseArticlesResponseBody(responseBody.string())
                    Log.d(TAG, "[handleNewsArticlesResponse] recipes parsed, attempt to save it in db")

                    postToHandleDbQueue(Runnable {
                        insertRecipes(recipes!!)
                        Log.d(TAG, "[handleNewsArticlesResponse] recipes saved in db")
                        LocalBroadcastManager.getInstance(App.context!!)
                                .sendBroadcast(Intent(RECIPES_RETRIEVED_SUCCESSFULLY))
                    })
                }
            })
        }

        override fun onSuccess(response: Response<ResponseBody?>?) {
            TODO("Not yet implemented")
        }

        override fun onFailure() {
            LocalBroadcastManager.getInstance(App.context!!)
                    .sendBroadcast(Intent(RECIPES_NOT_RETRIEVED))
        }

    })
}
