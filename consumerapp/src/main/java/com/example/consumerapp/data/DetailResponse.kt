package com.example.consumerapp.data

import com.google.gson.annotations.SerializedName

data class DetailResponse(

    @SerializedName("id")
    val userId: Int,

    @SerializedName("login")
    val username: String,

    @SerializedName("avatar_url")
    val avatar: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("company")
    val company: String,

    @SerializedName("location")
    val location: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("public_repos")
    val repository: Int,

    @SerializedName("followers")
    val followers: Int,

    @SerializedName("following")
    val following: Int

)