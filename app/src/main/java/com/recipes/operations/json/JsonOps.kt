package com.recipes.operations.json

import android.util.Log
import com.recipes.models.Recipes
import com.recipes.utils.*
import org.json.JSONArray
import org.json.JSONException
import java.util.ArrayList

private const val TAG = "JsonOps"

@Throws(JSONException::class)
fun parseArticlesResponseBody(response: String)/*: List<NewsArticle>?*/ {
    val responseJasonArray = JSONArray(response)
    val recipesList = ArrayList<Recipes>()

    for (i in 0 until responseJasonArray.length()){
        val child  = responseJasonArray.getJSONObject(i)
        Log.d(TAG,"[parseArticlesResponseBody] child: $child")
        if(child.has(RECIPES_CALORIES)){
            Log.d(TAG,"[parseArticlesResponseBody] RECIPES_CALORIES of child $i: ${child.get(RECIPES_CALORIES)}")
        }
        if(child.has(RECIPES_CARBO)){
            Log.d(TAG,"[parseArticlesResponseBody] RECIPES_CARBO of child $i: ${child.get(RECIPES_CARBO)}")
        }
        if(child.has(RECIPES_DESCRIPTION)){
            Log.d(TAG,"[parseArticlesResponseBody] RECIPES_DESCRIPTION of child $i: ${child.get(RECIPES_DESCRIPTION)}")
        }
        if(child.has(RECIPES_HEADLINE)){
            Log.d(TAG,"[parseArticlesResponseBody] RECIPES_HEADLINE of child $i: ${child.get(RECIPES_HEADLINE)}")
        }
        if(child.has(RECIPES_ID)){
            Log.d(TAG,"[parseArticlesResponseBody] RECIPES_ID of child $i: ${child.get(RECIPES_ID)}")
        }
        if(child.has(RECIPES_IMAGE)){
            Log.d(TAG,"[parseArticlesResponseBody] RECIPES_IMAGE of child $i: ${child.get(RECIPES_IMAGE)}")
        }
        if(child.has(RECIPES_NAME)){
            Log.d(TAG,"[parseArticlesResponseBody] RECIPES_NAME of child $i: ${child.get(RECIPES_NAME)}")
        }
        if(child.has(RECIPES_PROTEINS)){
            Log.d(TAG,"[parseArticlesResponseBody] RECIPES_PROTEINS of child $i: ${child.get(RECIPES_PROTEINS)}")
        }
        if(child.has(RECIPES_THUMB)){
            Log.d(TAG,"[parseArticlesResponseBody] RECIPES_THUMB of child $i: ${child.get(RECIPES_THUMB)}")
        }
        if(child.has(RECIPES_TIME)){
            Log.d(TAG,"[parseArticlesResponseBody] RECIPES_TIME of child $i: ${child.get(RECIPES_TIME)}")
        }
        if(child.has(RECIPES_DIFFICULTY)){
            Log.d(TAG,"[parseArticlesResponseBody] RECIPES_DIFFICULTY of child $i: ${child.get(RECIPES_DIFFICULTY)}")
        }

    }
//    return articles
}
