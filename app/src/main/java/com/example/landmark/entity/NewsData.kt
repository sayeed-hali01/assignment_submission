package com.example.landmark.entity

import com.example.example.Data
import com.google.gson.annotations.SerializedName

data class NewsData(

    @SerializedName("meta") var meta: Meta? = Meta(),
    @SerializedName("data") var data: ArrayList<Data> = arrayListOf()

)