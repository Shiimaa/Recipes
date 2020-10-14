package com.recipes.operations.db

import com.recipes.interfaces.LocalDbLoaded
import com.recipes.models.Recipes

fun getAllRecipes(callback: LocalDbLoaded?) {
    if (callback == null) throw RuntimeException("Callback is NULL!")

    val articles: List<Recipes> = AppDatabase.getInstance()!!.recipesDao().getAllRecipes()

    callback.onLocalDataLoaded(articles)
}

fun getAllRecipesByCalories(callback: LocalDbLoaded?) {
    if (callback == null) throw RuntimeException("Callback is NULL!")

    val articles: List<Recipes> = AppDatabase.getInstance()!!.recipesDao().getAllRecipesByCalories()

    callback.onLocalDataLoaded(articles)
}

fun getAllRecipesByFats(callback: LocalDbLoaded?) {
    if (callback == null) throw RuntimeException("Callback is NULL!")

    val articles: List<Recipes> = AppDatabase.getInstance()!!.recipesDao().getAllRecipesByFats()

    callback.onLocalDataLoaded(articles)
}

fun insertRecipe(recipe: Recipes) {
    AppDatabase.getInstance()!!.recipesDao().insertRecipe(recipe)
}

fun insertRecipes(recipes: List<Recipes>) {
    AppDatabase.getInstance()!!.recipesDao().insertRecipes(recipes)
}