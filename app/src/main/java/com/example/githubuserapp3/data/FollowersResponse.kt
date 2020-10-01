package com.example.githubuserapp3.data

import com.google.gson.annotations.SerializedName

data class FollowersResponse(

    @SerializedName("avatar_url")
    val avatar: String,

    @SerializedName("login")
    val username: String,

    @SerializedName("type")
    val type: String

)