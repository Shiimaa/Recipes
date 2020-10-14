package com.recipes.interfaces

import com.recipes.models.Recipes

interface LocalDbLoaded {
    fun onLocalDataLoaded(recipes: List<Recipes>)

}