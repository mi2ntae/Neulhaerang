package com.finale.neulhaerang.domain

import android.annotation.SuppressLint
import androidx.navigation.NavHostController

object MyPageController {
    @SuppressLint("StaticFieldLeak")
    private var navController: NavHostController? = null

    fun setNavController(value: NavHostController) {
        navController = value
    }

    fun back() {
        navController?.popBackStack()
    }
}