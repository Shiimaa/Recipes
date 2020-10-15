package com.recipes.ui.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipes.R
import com.recipes.adapter.RecipesRecyclerViewAdapter
import com.recipes.application.App
import com.recipes.dispatchqueues.synchronous.postTOUiThread
import com.recipes.dispatchqueues.synchronous.postToHandleDbQueue
import com.recipes.interfaces.LocalDbLoaded
import com.recipes.models.Recipes
import com.recipes.operations.db.getAllRecipes
import com.recipes.operations.network.getRecipes
import com.recipes.utils.RECIPES_NOT_RETRIEVED
import com.recipes.utils.RECIPES_RETRIEVED_SUCCESSFULLY
import com.recipes.utils.dp2Px


class RecipesListFragment : Fragment() {
    private val TAG = "RecipesListFragment"
    private lateinit var recipesRecyclerView: RecyclerView
    private lateinit var searchButton: ImageView
    private lateinit var sortButton: ImageView
    private var recipesAdapter: RecipesRecyclerViewAdapter? = null

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null && !intent.action.isNullOrEmpty()) {
                when (intent.action) {
                    RECIPES_RETRIEVED_SUCCESSFULLY -> loadData()
                    RECIPES_NOT_RETRIEVED -> {
                        Toast.makeText(context, "There is an error in internet connction, please try later", Toast.LENGTH_LONG).show()
                        Log.d(TAG, "[BroadcastReceiver] There is error in loading data")
                    }
                }
            }
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val inflatedView = inflater.inflate(R.layout.fragment_recipes_list, container, false)

        // Initialize the views
        recipesRecyclerView = inflatedView.findViewById(R.id.recipesRecyclerView)
        searchButton = inflatedView.findViewById(R.id.searchIcon)
        sortButton = inflatedView.findViewById(R.id.sortIcon)

        registerBroadcast()

        return inflatedView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recipesAdapter = RecipesRecyclerViewAdapter()
        recipesRecyclerView.layoutManager = LinearLayoutManager(context)
        recipesRecyclerView.adapter = recipesAdapter

        // Add top space for first item
        recipesRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                // check if position item is first item
                // add top margin to 8dp
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.top = dp2Px(8)
                }
            }
        })

        loadData()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        LocalBroadcastManager.getInstance(App.context!!)
                .unregisterReceiver(receiver)
    }

    private fun loadData() {
        postToHandleDbQueue(Runnable {
            getAllRecipes(object : LocalDbLoaded {
                override fun onLocalDataLoaded(recipes: List<Recipes>) {
                    // if received list not empty, update adapter
                    // else request data from backend
                    if (recipes.isNotEmpty()) {
                        postTOUiThread(Runnable {
                            // add retrieved recipes to adapter
                            recipesAdapter?.updateRecipesList(recipes)
                            // notify adapter that there is changing in data
                            recipesAdapter?.notifyDataSetChanged()
                        })
                    } else {
                        getRecipes()
                    }
                }
            })
        })
    }

    private fun registerBroadcast() {
        val intent = IntentFilter()
        intent.addAction(RECIPES_NOT_RETRIEVED)
        intent.addAction(RECIPES_RETRIEVED_SUCCESSFULLY)

        LocalBroadcastManager.getInstance(App.context!!)
                .registerReceiver(receiver, intent)
    }

}