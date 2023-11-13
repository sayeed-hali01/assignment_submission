package com.example.landmark.entity

import com.google.gson.annotations.SerializedName


data class Meta(

    @SerializedName("found") var found: Int? = null,
    @SerializedName("returned") var returned: Int? = null,
    @SerializedName("limit") var limit: Int? = null,
    @SerializedName("page") var page: Int? = null

)