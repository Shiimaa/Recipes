package com.recipes.utils

import com.recipes.application.App

fun dp2Px(dp: Int): Int {
    return (dp * App.context!!.resources.displayMetrics.density).toInt()
}
