package com.recipes.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Recipes {

    @PrimaryKey
    private var id: String? = null

    private var name: String? = null
    private var description: String? = null
    private var headLine: String? = null
    private var calories: String? = null
    private var carbos: String? = null
    private var fats: String? = null
    private var imageUrl: String? = null
    private var thumbUrl: String? = null
    private var proteins: String? = null
    private var time: String? = null
    private var difficulty: Int = 0

    constructor(id: String?, name: String?, description: String?, headLine: String?, calories: String?, carbos: String?, fats: String?, imageUrl: String?, thumbUrl: String?, proteins: String?, time: String?, difficulty: Int) {
        this.id = id
        this.name = name
        this.description = description
        this.headLine = headLine
        this.calories = calories
        this.carbos = carbos
        this.fats = fats
        this.imageUrl = imageUrl
        this.thumbUrl = thumbUrl
        this.proteins = proteins
        this.time = time
        this.difficulty = difficulty
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getId(): String? {
        return id
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getName(): String? {
        return name
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getDescription(): String? {
        return description
    }

    fun setHeadLine(headLine: String?) {
        this.headLine = headLine
    }

    fun getHeadLine(): String? {
        return headLine
    }

    fun setCaloriesId(calories: String?) {
        this.calories = calories
    }

    fun getCalories(): String? {
        return calories
    }

    fun setCarbos(carbos: String?) {
        this.carbos = carbos
    }

    fun getCarbos(): String? {
        return carbos
    }

    fun setFats(fats: String?) {
        this.fats = fats
    }

    fun getFats(): String? {
        return fats
    }

    fun setImageUrl(imageUrl: String?) {
        this.imageUrl = imageUrl
    }

    fun getImageUrl(): String? {
        return imageUrl
    }

    fun setThumbUrl(thumbUrl: String?) {
        this.thumbUrl = thumbUrl
    }

    fun getThumbUrl(): String? {
        return thumbUrl
    }

    fun setProteins(proteins: String?) {
        this.proteins = proteins
    }

    fun getProteins(): String? {
        return proteins
    }

    fun setTime(time: String?) {
        this.time = time
    }

    fun getTime(): String? {
        return time
    }

    fun setDifficulty(difficulty: Int) {
        this.difficulty = difficulty
    }

    fun getDifficulty(): Int {
        return difficulty
    }

}