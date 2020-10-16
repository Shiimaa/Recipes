package com.recipes.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recipes.R
import com.recipes.application.App
import com.recipes.models.Recipes
import com.recipes.utils.CLICKED_RECIPE_ACTION
import com.recipes.utils.CLICKED_RECIPE_ID
import java.util.ArrayList

class RecipesRecyclerViewAdapter : RecyclerView.Adapter<RecipesRecyclerViewAdapter.RecipesViewHolder>() {
    private var recipesList = ArrayList<Recipes>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        return RecipesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recipe_item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return recipesList.size
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        holder.recipeName.text = recipesList[position].name
        Glide
                .with(App.context!!)
                .load(recipesList[position].thumbUrl)
                .into(holder.recipeImage)

        holder.itemView.setOnClickListener {
            LocalBroadcastManager.getInstance(App.context!!)
                    .sendBroadcast(Intent(CLICKED_RECIPE_ACTION).putExtra(CLICKED_RECIPE_ID, recipesList[position].id))
        }
    }

    override fun onViewRecycled(holder: RecipesViewHolder) {
        Glide
                .with(App.context!!)
                .clear(holder.recipeImage)

        super.onViewRecycled(holder)
    }

    fun updateRecipesList(newRecipesList: List<Recipes>) {
        recipesList.addAll(newRecipesList)
    }

    class RecipesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeImage: ImageView = itemView.findViewById(R.id.recipeImage)
        val recipeName: TextView = itemView.findViewById(R.id.recipeName)


    }
}