package com.recipes.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.recipes.models.Recipes

@Dao
interface RecipesDao {

    @Query("SELECT * FROM RECIPES")
    fun getAllRecipes(): List<Recipes>

    @Query("SELECT * FROM RECIPES ORDER BY calories ")
    fun getAllRecipesByCalories(): List<Recipes>

    @Query("SELECT * FROM RECIPES ORDER BY fats")
    fun getAllRecipesByFats(): List<Recipes>

    @Query("SELECT * FROM RECIPES WHERE id= :recipeId")
    fun getAllRecipeById(recipeId:String): Recipes

    @Insert
    fun insertRecipe(recipe: Recipes)

    @Insert
    fun insertRecipes(recipes: List<Recipes>)
}