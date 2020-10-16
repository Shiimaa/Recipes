package com.recipes.operations.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.recipes.application.App
import com.recipes.daos.KeyValueDao
import com.recipes.daos.RecipesDao
import com.recipes.models.KeyValue
import com.recipes.models.Recipes

@Database(entities = [Recipes::class, KeyValue::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipesDao() : RecipesDao
    abstract fun keyValueDao() : KeyValueDao

    companion object{
        private var db: AppDatabase? = null
        private val mutex = Any()

        fun getInstance(): AppDatabase? {
            if (db == null) {
                synchronized(mutex) {
                    if (db == null) {
                        db = Room.databaseBuilder(App.context!!, AppDatabase::class.java, "local.db").build()
                    }
                }
            }
            return db
        }

    }
}