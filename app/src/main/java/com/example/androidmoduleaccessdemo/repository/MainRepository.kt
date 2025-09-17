package com.example.androidmoduleaccessdemo.repository

import android.content.Context
import com.example.androidmoduleaccessdemo.common.utils.CommonUtils
import com.example.androidmoduleaccessdemo.model.UserDataWrapper
import com.google.gson.Gson

class MainRepository(private val context: Context) {

    fun getUserDataFromAssets(): UserDataWrapper {
        val gson = Gson()
        val myJson = CommonUtils.getJsonDataFromAsset(context, "ModulesList.json")
        return gson.fromJson(myJson, UserDataWrapper::class.java)
    }

}