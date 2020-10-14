package com.recipes.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.recipes.R
import com.recipes.operations.network.getRecipes

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main)

        getRecipes()
    }
}