package com.finale.neulhaerang.data

import DataStoreModule
import android.app.Application

open class DataStoreApplication: Application() {
    private lateinit var dataStore: DataStoreModule
    companion object{
        private lateinit var dataStoreApplication: DataStoreApplication
        fun getInstance() : DataStoreApplication = dataStoreApplication
    }
    override fun onCreate(){
        super.onCreate()
        dataStore = DataStoreModule(this)
        dataStoreApplication = this
    }
    fun getDataStore() : DataStoreModule = dataStore
}