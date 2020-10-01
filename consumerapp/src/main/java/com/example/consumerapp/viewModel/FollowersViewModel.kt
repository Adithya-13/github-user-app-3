package com.example.consumerapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.consumerapp.BuildConfig
import com.example.consumerapp.data.FollowersResponse
import com.example.consumerapp.service.DataResource
import com.example.consumerapp.service.NetworkProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {

    private val tag = FollowersViewModel::class.simpleName

    val listUserFollowers = MutableLiveData<ArrayList<FollowersResponse>>()

    fun setFollowersUser(query: String) {

        val dataSource = NetworkProvider.providesHttpAdapter().create(DataResource::class.java)
        dataSource.followerUser(query)
            .enqueue(object : Callback<ArrayList<FollowersResponse>> {
                override fun onResponse(
                    call: Call<ArrayList<FollowersResponse>>,
                    response: Response<ArrayList<FollowersResponse>>
                ) {
                    val items = response.body()
                    listUserFollowers.postValue(items)
                    Log.d(tag, "setFollowersUser: onResponse")
                }

                override fun onFailure(call: Call<ArrayList<FollowersResponse>>, t: Throwable) {
                    Log.d(tag, "setFollowersUser: onFailure = $t")
                }
            })
    }

    fun getFollowersUser(): LiveData<ArrayList<FollowersResponse>> {
        Log.d(tag, "getFollowersUser")
        return listUserFollowers
    }
}