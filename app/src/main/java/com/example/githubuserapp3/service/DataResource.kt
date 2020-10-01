package com.example.githubuserapp3.service

import com.example.githubuserapp3.data.DetailResponse
import com.example.githubuserapp3.data.FollowersResponse
import com.example.githubuserapp3.data.FollowingResponse
import com.example.githubuserapp3.data.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DataResource {

    @GET("search/users")
    fun searchUser(
        @Query("q") username: String?
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun detailUser(
        @Path("username") username: String?
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun followerUser(
        @Path("username") username: String?
    ): Call<ArrayList<FollowersResponse>>

    @GET("users/{username}/following")
    fun followingUser(
        @Path("username") username: String?
    ): Call<ArrayList<FollowingResponse>>
}
