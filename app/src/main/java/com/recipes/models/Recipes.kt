package com.recipes.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Recipes {

    @PrimaryKey
    @NonNull
    var id: String? = ""

    var name: String? = null
    var description: String? = null
    var headLine: String? = null
    var calories: String? = null
    var carbos: String? = null
    var fats: String? = null
    var imageUrl: String? = null
    var thumbUrl: String? = null
    var proteins: String? = null
    var time: String? = null
    var difficulty: Int = 0
}