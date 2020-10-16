package com.recipes.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.recipes.R
import com.recipes.application.App
import com.recipes.dispatchqueues.synchronous.postTOUiThread
import com.recipes.dispatchqueues.synchronous.postToHandleDbQueue
import com.recipes.interfaces.SearchedDbLoaded
import com.recipes.models.Recipes
import com.recipes.operations.db.getRecipeById
import com.recipes.utils.CLICKED_RECIPE_ID

class RecipesDetailsFragment : Fragment() {
    private lateinit var recipeImage: ImageView
    private lateinit var recipeName: TextView
    private lateinit var recipeHeadline: TextView
    private lateinit var recipeDescription: TextView
    private lateinit var recipeCal: TextView
    private lateinit var recipeCarb: TextView
    private lateinit var recipeFat: TextView
    private lateinit var recipeProt: TextView
    private lateinit var recipeTime: TextView
    private lateinit var backButton: ImageView
    private var clickedRecipeId: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val inflatedView = inflater.inflate(R.layout.fragment_recipes_details, container, false)

        // Initialize views
        recipeImage = inflatedView.findViewById(R.id.recipeImage)
        recipeName = inflatedView.findViewById(R.id.recipeName)
        recipeHeadline = inflatedView.findViewById(R.id.recipeHeadline)
        recipeDescription = inflatedView.findViewById(R.id.recipeDescription)
        recipeCal = inflatedView.findViewById(R.id.caloriesValue)
        recipeCarb = inflatedView.findViewById(R.id.carbosValue)
        recipeFat = inflatedView.findViewById(R.id.fatsValue)
        recipeProt = inflatedView.findViewById(R.id.proteinsValue)
        recipeTime = inflatedView.findViewById(R.id.timeValue)
        backButton = inflatedView.findViewById(R.id.backIcon)

        val arguments = arguments
        if (arguments != null) {
            clickedRecipeId = arguments.getString(CLICKED_RECIPE_ID, "")
        }

        setClickListener()

        return inflatedView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postToHandleDbQueue(Runnable {
            getRecipeById(clickedRecipeId,object : SearchedDbLoaded {
                override fun onSelectedItemLoaded(recipe: Recipes) {
                    postTOUiThread(Runnable {
                        updateUi(recipe)
                    })
                }

                override fun onSearchedItemsLoaded(recipes: List<Recipes>) {
                }
            })
        })

    }

    private fun updateUi(recipe: Recipes) {
        Glide.with(App.context!!)
                .load(recipe.imageUrl)
                .into(recipeImage)

        recipeName.text = recipe.name
        recipeHeadline.text = recipe.headLine
        recipeDescription.text = recipe.description
        recipeCal.text = recipe.calories
        recipeCarb.text = recipe.carbos
        recipeFat.text = recipe.fats
        recipeProt.text = recipe.proteins
        recipeTime.text = recipe.time
    }

    private fun setClickListener() {
        backButton.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}