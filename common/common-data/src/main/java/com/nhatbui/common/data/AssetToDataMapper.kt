package com.nhatbui.common.data

import android.content.Context
import com.google.gson.Gson
import java.lang.reflect.Type

class AssetToDataMapper<RESULT>(
    private val context: Context,
    private val gson: Gson,
    private val type: Type
) {
    fun getDataFromJson(assetName: String): RESULT {
        val jsonString = context.assets.open(assetName).bufferedReader().use { it.readText() }
        return gson.fromJson(jsonString, type)
    }
}
