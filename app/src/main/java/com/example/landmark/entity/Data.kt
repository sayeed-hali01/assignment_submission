package com.example.example

import com.google.gson.annotations.SerializedName


data class Data(
    @SerializedName("uuid") var uuid: String?= null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("keywords") var keywords: String? = null,
    @SerializedName("snippet") var snippet: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("image_url") var imageUrl: String? = null,
    @SerializedName("language") var language: String? = null,
    @SerializedName("published_at") var publishedAt: String? = null,
    @SerializedName("source") var source: String? = null,
    @SerializedName("categories") var categories: ArrayList<String> = arrayListOf(),
    @SerializedName("relevance_score") var relevanceScore: String? = null,
    @SerializedName("locale") var locale: String? = null

)