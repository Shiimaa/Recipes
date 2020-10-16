package com.recipes.ui.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.recipes.R
import com.recipes.application.App
import com.recipes.ui.fragments.RecipesDetailsFragment
import com.recipes.ui.fragments.RecipesListFragment
import com.recipes.utils.CLICKED_RECIPE_ACTION
import com.recipes.utils.CLICKED_RECIPE_ID
import com.recipes.utils.RECIPES_NOT_RETRIEVED
import com.recipes.utils.RECIPES_RETRIEVED_SUCCESSFULLY

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null && !intent.action.isNullOrEmpty()) {
                if (intent.action == CLICKED_RECIPE_ACTION) {
                    launchDetailedFragment(intent.getStringExtra(CLICKED_RECIPE_ID))
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main)

        launchMainFragment()
        registerBroadcast()
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(App.context!!)
                .unregisterReceiver(receiver)
    }

    private fun launchMainFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainContainer, RecipesListFragment())
                .commit()
    }

    private fun launchDetailedFragment(clickedRecipeId: String?) {
        if (clickedRecipeId == null)
            throw RuntimeException("Clicked recipe id is Null!")

        val data = Bundle()
        data.putString(CLICKED_RECIPE_ID, clickedRecipeId)
        val fragment = RecipesDetailsFragment()
        fragment.arguments = data

        supportFragmentManager
                .beginTransaction()
                .add(R.id.mainContainer, fragment)
                .commit()

    }

    private fun registerBroadcast() {
        val intent = IntentFilter()
        intent.addAction(CLICKED_RECIPE_ACTION)

        LocalBroadcastManager.getInstance(App.context!!)
                .registerReceiver(receiver, intent)

    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.mainContainer)

        if (currentFragment is RecipesDetailsFragment) {
            Log.d(TAG, "[onBackPressed] currentFragment is RecipesDetailsFragment, close it ")
            supportFragmentManager.beginTransaction()
                    .detach(currentFragment)
                    .commit()
        } else {
            if ((currentFragment as RecipesListFragment).isSearchViewClicked())
                (currentFragment as RecipesListFragment).closeKeyboard()
            else
                super.onBackPressed()

        }
    }
}