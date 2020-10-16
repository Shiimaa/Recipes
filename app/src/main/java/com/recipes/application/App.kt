package com.recipes.application

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.recipes.dispatchqueues.synchronous.postToHandleDbQueue
import com.recipes.operations.db.updateSortTypeKey

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

//        postToHandleDbQueue(Runnable {
//            updateSortTypeKey()
//        })
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        @Volatile
        var context: Context? = null
    }

}