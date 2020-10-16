package com.recipes.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class KeyValue {
    @PrimaryKey
    @NonNull
    var id: String = ""

    var value: Int = 0
}