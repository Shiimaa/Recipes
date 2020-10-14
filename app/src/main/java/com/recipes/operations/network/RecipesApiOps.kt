package com.recipes.operations.network

import android.util.Log
import com.recipes.api.ApiOperations.retrieveArticlesList
import com.recipes.api.ResponseCode
import com.recipes.api.ResponseCode.checkResponseCode
import com.recipes.dispatchqueues.synchronous.postToHandleNetworkResponseQueue
import com.recipes.dispatchqueues.synchronous.postToNetworkQueue
import com.recipes.operations.json.parseArticlesResponseBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "RecipesApiOps"
fun getRecipes(){
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
    checkResponseCode(response, object :ResponseCode.ResponseCodeCallback{
        override fun onResponse(response: Response<ResponseBody?>?) {
            postToHandleNetworkResponseQueue(Runnable {
                Log.d(TAG, "[handleNewsArticlesResponse] attempt to parse json object")
                val responseBody = response?.body()
                if (responseBody != null) {
                    parseArticlesResponseBody(responseBody.string())
//                    try {
//                        val articles: List<NewsArticle> = JsonOps.parseArticlesResponseBody(responseBody.string())
//                        Log.d(TAG, "[handleNewsArticlesResponse] parsed articles size: " + articles.size)
//                        AppQueues.postToDbQueue(Runnable {
//                            NewsArticlesOperations.insertArticles(articles)
//                            LocalBroadcastManager.getInstance(App.context).sendBroadcast(Intent(Constants.INTENT_ACTION_DATA_FETCHED_FROM_NETWORK))
//                        })
//                    } catch (e: IOException) {
//                        Log.e(TAG, "[handleNewsArticlesResponse] ERROR: " + Log.getStackTraceString(e))
//                    } catch (e: JSONException) {
//                        Log.e(TAG, "[handleNewsArticlesResponse] ERROR: " + Log.getStackTraceString(e))
//                    }
                }
            })        }

        override fun onSuccess(response: Response<ResponseBody?>?) {
            TODO("Not yet implemented")
        }

        override fun onFailure() {
            TODO("Not yet implemented")
        }

        override fun onUnknown() {
            TODO("Not yet implemented")
        }
    })
}
