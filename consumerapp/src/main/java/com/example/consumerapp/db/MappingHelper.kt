package com.example.consumerapp.db

import android.database.Cursor
import android.util.Log
import com.example.consumerapp.data.entity.Favorite
import com.example.consumerapp.db.DatabaseContract.NoteColumns.Companion.AVATAR
import com.example.consumerapp.db.DatabaseContract.NoteColumns.Companion.TYPE
import com.example.consumerapp.db.DatabaseContract.NoteColumns.Companion.USERNAME
import com.example.consumerapp.db.DatabaseContract.NoteColumns.Companion.USER_ID

object MappingHelper {

    private val tag = MappingHelper::class.simpleName

    fun mapCursorToArrayList(favoriteCursor: Cursor?): ArrayList<Favorite> {
        Log.d(tag, "mapCursorToArrayList")
        val favoriteList = ArrayList<Favorite>()
        favoriteCursor?.apply {
            while (moveToNext()) {
                val userId = getInt(getColumnIndexOrThrow(USER_ID))
                val username = getString(getColumnIndexOrThrow(USERNAME))
                val type = getString(getColumnIndexOrThrow(TYPE))
                val avatar = getString(getColumnIndexOrThrow(AVATAR))
                favoriteList.add(Favorite(userId, username, type, avatar))
            }
        }
        return favoriteList
    }
}