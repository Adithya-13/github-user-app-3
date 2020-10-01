package com.example.consumerapp.viewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.consumerapp.data.entity.Favorite
import com.example.consumerapp.db.DatabaseContract
import com.example.consumerapp.db.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteViewModel : ViewModel() {

    private val tag = FavoriteViewModel::class.simpleName
    private val queryAll = MutableLiveData<ArrayList<Favorite>>()
    private val queryById = MutableLiveData<ArrayList<Favorite>>()

    fun getQueryAll(context: Context): LiveData<ArrayList<Favorite>> {
        Log.d(tag, "getQueryAll")
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
        Log.d(tag, "getQueryById")
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