package com.recipes.operations.db

import android.util.Log
import com.recipes.interfaces.LocalKeyValueDbLoaded
import com.recipes.models.KeyValue
import com.recipes.utils.SORT_TYPE_KEY

private const val TAG = "KeyValueOperations"
var currentSortType: Int = -1
fun updateSortTypeKey(callback: LocalKeyValueDbLoaded?) {
    if (callback == null) throw RuntimeException("Callback is NULL!")

    if (currentSortType == -1) {
        val newValue = AppDatabase.getInstance()!!.keyValueDao().getValue(SORT_TYPE_KEY)
        callback.onLocalDataLoaded(newValue)
    }

    Log.d(TAG, "[updateSortTypeKey] currentSortType:$currentSortType")
}

fun insertKey(newValue: Int) {
    val savedValue = AppDatabase.getInstance()!!.keyValueDao().getValue(SORT_TYPE_KEY)
    Log.d(TAG, "[insertKey] savedValue:$savedValue")
    if (savedValue == 0) {
        val keyValue = KeyValue()
        keyValue.id = SORT_TYPE_KEY
        keyValue.value = newValue
        AppDatabase.getInstance()!!.keyValueDao().insertKey(keyValue)
    } else {
        AppDatabase.getInstance()!!.keyValueDao().updateKey(newValue, SORT_TYPE_KEY)
    }
}