package com.recipes.operations.db

import com.recipes.interfaces.LocalDbLoaded
import com.recipes.interfaces.SearchedDbLoaded
import com.recipes.models.Recipes

fun getAllRecipes(callback: LocalDbLoaded?) {
    if (callback == null) throw RuntimeException("Callback is NULL!")

    val recipes: List<Recipes> = AppDatabase.getInstance()!!.recipesDao().getAllRecipes()

    callback.onLocalDataLoaded(recipes)
}

fun getAllRecipesByCalories(callback: LocalDbLoaded?) {
    if (callback == null) throw RuntimeException("Callback is NULL!")

    val recipes: List<Recipes> = AppDatabase.getInstance()!!.recipesDao().getAllRecipesByCalories()

    callback.onLocalDataLoaded(recipes)
}

fun getAllRecipesByFats(callback: LocalDbLoaded?) {
    if (callback == null) throw RuntimeException("Callback is NULL!")

    val recipes: List<Recipes> = AppDatabase.getInstance()!!.recipesDao().getAllRecipesByFats()

    callback.onLocalDataLoaded(recipes)
}

fun getRecipeById(recipeId: String, callback: SearchedDbLoaded) {
    if (callback == null) throw RuntimeException("Callback is NULL!")

    val selectedRecipe: Recipes = AppDatabase.getInstance()!!.recipesDao().getAllRecipeById(recipeId)

    callback.onSelectedItemLoaded(selectedRecipe)

}

fun insertRecipe(recipe: Recipes) {
    AppDatabase.getInstance()!!.recipesDao().insertRecipe(recipe)
}

fun insertRecipes(recipes: List<Recipes>) {
    AppDatabase.getInstance()!!.recipesDao().insertRecipes(recipes)
}