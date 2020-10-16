package com.recipes.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.recipes.models.KeyValue

@Dao
interface KeyValueDao {

    @Query("SELECT value FROM KeyValue WHERE id= :key")
    fun getValue(key: String): Int

    @Insert
    fun insertKey(keyValue: KeyValue)

    @Query("UPDATE KeyValue SET value = :value WHERE id = :key")
    fun updateKey(value: Int, key: String)
}