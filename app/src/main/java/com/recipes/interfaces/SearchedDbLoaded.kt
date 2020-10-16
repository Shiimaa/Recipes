package com.recipes.interfaces

import com.recipes.models.Recipes

interface SearchedDbLoaded {
    fun onSelectedItemLoaded(recipe: Recipes)
    fun onSearchedItemsLoaded(recipes: List<Recipes>)
}