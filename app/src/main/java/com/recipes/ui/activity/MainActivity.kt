package com.recipes.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.recipes.R
import com.recipes.operations.network.getRecipes
import com.recipes.ui.fragments.RecipesListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main)

//        getRecipes()
        launchMainFragment()
    }

    private fun launchMainFragment(){
        supportFragmentManager
                .beginTransaction()
                .add(R.id.mainContainer,RecipesListFragment())
                .commit()
    }
}