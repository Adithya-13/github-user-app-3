package com.example.githubuserapp3.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp3.data.Items
import com.example.githubuserapp3.data.SearchResponse
import com.example.githubuserapp3.service.DataResource
import com.example.githubuserapp3.service.NetworkProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private val tag = SearchViewModel::class.simpleName

    val listUserSearch = MutableLiveData<ArrayList<Items>>()

    fun setSearchUser(query: String) {

        val dataSource = NetworkProvider.providesHttpAdapter().create(DataResource::class.java)
        dataSource.searchUser(query).enqueue(object :
            Callback<SearchResponse> {

            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                val items = response.body()?.items
                listUserSearch.postValue(items)
                Log.d(tag, "setSearchUser: onResponse")
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.d(tag, "setSearchUser: onFailure = $t")
            }

        })
    }

    fun getSearchUser(): LiveData<ArrayList<Items>> {
        Log.d(tag, "getSearchUser")
        return listUserSearch
    }

}