package com.example.githubuserapp3.viewModel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserapp3.data.entity.Favorite
import com.example.githubuserapp3.db.DatabaseContract
import com.example.githubuserapp3.db.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteViewModel : ViewModel() {

    private val queryAll = MutableLiveData<ArrayList<Favorite>>()
    private val queryById = MutableLiveData<ArrayList<Favorite>>()

    fun getQueryAll(context: Context): LiveData<ArrayList<Favorite>> {
        viewModelScope.launch(Dispatchers.Main) {
            val deferredQueryAll = async(Dispatchers.IO) {
                val cursor = context.contentResolver?.query(
                    DatabaseContract.NoteColumns.CONTENT_URI,
                    null,
                    null,
                    null,
                    null
                )
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val get = deferredQueryAll.await()
            queryAll.postValue(get)
        }
        return queryAll
    }

    fun getQueryById(context: Context, uriWithId: Uri): LiveData<ArrayList<Favorite>> {
        viewModelScope.launch(Dispatchers.Main) {
            val deferredQueryById = async(Dispatchers.IO) {
                val cursor = context.contentResolver?.query(uriWithId, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val get = deferredQueryById.await()
            queryById.postValue(get)
        }
        return queryById
    }
}