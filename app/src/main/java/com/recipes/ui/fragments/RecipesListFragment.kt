package com.recipes.ui.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getSystemService
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
import com.recipes.interfaces.LocalKeyValueDbLoaded
import com.recipes.models.Recipes
import com.recipes.operations.db.*
import com.recipes.operations.network.getRecipes
import com.recipes.utils.*


class RecipesListFragment : Fragment() {
    private val TAG = "RecipesListFragment"
    private lateinit var recipesRecyclerView: RecyclerView
    private lateinit var searchButton: ImageView
    private lateinit var sortButton: ImageView
    private lateinit var screenTitle: TextView
    private lateinit var homeDetailsLayout: ConstraintLayout
    private lateinit var searchLayout: ConstraintLayout
    private lateinit var closeButton: ImageView
    private lateinit var searchText: EditText

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
        screenTitle = inflatedView.findViewById(R.id.screenTitle)
        homeDetailsLayout = inflatedView.findViewById(R.id.homeDetailsLayout)
        searchLayout = inflatedView.findViewById(R.id.searchLayout)
        closeButton = inflatedView.findViewById(R.id.closeIcon)
        searchText = inflatedView.findViewById(R.id.searchEditText)

        registerBroadcast()

        return inflatedView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setViewsClickListener()

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
            Log.d(TAG, "[loadData] currentSortType:$currentSortType")

            when (currentSortType) {
                -1 -> {
                    updateSortTypeKey(object : LocalKeyValueDbLoaded {
                        override fun onLocalDataLoaded(value: Int) {
                            Log.d(TAG, "[onLocalDataLoaded] currentSortType:$currentSortType")

                            currentSortType = value
                            loadData()
                        }
                    })

                }
                SORT_BY_CAL -> getAllRecipesByCalories(object : LocalDbLoaded {
                    override fun onLocalDataLoaded(recipes: List<Recipes>) {
                        checkRetrievedData(recipes)
                    }
                })
                SORT_BY_FAT -> getAllRecipesByFats(object : LocalDbLoaded {
                    override fun onLocalDataLoaded(recipes: List<Recipes>) {
                        checkRetrievedData(recipes)
                    }
                })
                else -> getAllRecipes(object : LocalDbLoaded {
                    override fun onLocalDataLoaded(recipes: List<Recipes>) {
                        checkRetrievedData(recipes)
                    }
                })
            }
        })
    }

    private fun checkRetrievedData(recipes: List<Recipes>) {
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

    private fun setViewsClickListener() {
        searchButton.setOnClickListener {
            showSearchLayout()
        }

        sortButton.setOnClickListener {
            createCustomPopupMenu()
        }

        closeButton.setOnClickListener {
            closeKeyboard()
        }
    }

    private fun showSearchLayout() {
        if (searchLayout.visibility == View.GONE) {
            searchLayout.visibility = View.VISIBLE
            homeDetailsLayout.visibility = View.GONE
            searchText.requestFocus()
            activity?.showKeyboard(searchText)
        } else {
            searchLayout.visibility = View.GONE
            homeDetailsLayout.visibility = View.VISIBLE
        }
    }

    private fun createCustomPopupMenu() {
        //Creating the instance of PopupMenu
        val menu = PopupMenu(context, sortButton)
        //Inflating the Popup using xml file
        menu.menuInflater
                .inflate(R.menu.sort_items_menu, menu.menu);

        //registering popup with OnMenuItemClickListener
        menu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.sortCal -> {
                    currentSortType = SORT_BY_CAL
                    sortItems(SORT_BY_CAL)
                }
                R.id.sortFat -> {
                    currentSortType = SORT_BY_FAT
                    sortItems(SORT_BY_FAT)
                }

            }
            true
        })

        //showing popup menu
        menu.show()


    }

    private fun sortItems(sortType: Int) {
        postToHandleDbQueue(Runnable {
            if (sortType == SORT_BY_CAL) {
                insertKey(SORT_BY_CAL)
                getAllRecipesByCalories(object : LocalDbLoaded {
                    override fun onLocalDataLoaded(recipes: List<Recipes>) {
                        if (recipes.isNotEmpty())
                            postTOUiThread(Runnable {
                                recipesAdapter?.clearRecipesList()
                                recipesAdapter?.updateRecipesList(recipes)
                                recipesAdapter?.notifyDataSetChanged()
                            })
                    }
                })
            } else {
                insertKey(SORT_BY_FAT)
                getAllRecipesByFats(object : LocalDbLoaded {
                    override fun onLocalDataLoaded(recipes: List<Recipes>) {
                        if (recipes.isNotEmpty())
                            postTOUiThread(Runnable {
                                recipesAdapter?.clearRecipesList()
                                recipesAdapter?.updateRecipesList(recipes)
                                recipesAdapter?.notifyDataSetChanged()
                            })
                    }
                })
            }
        })
    }

    private fun registerBroadcast() {
        val intent = IntentFilter()
        intent.addAction(RECIPES_NOT_RETRIEVED)
        intent.addAction(RECIPES_RETRIEVED_SUCCESSFULLY)

        LocalBroadcastManager.getInstance(App.context!!)
                .registerReceiver(receiver, intent)
    }

    fun isSearchViewClicked(): Boolean {
        return searchLayout.visibility == View.VISIBLE
    }

    fun closeKeyboard() {
        searchLayout.visibility = View.GONE
        homeDetailsLayout.visibility = View.VISIBLE
        searchText.setText("")
        activity?.hideKeyboard(searchText)

    }
}