package com.example.githubuserapp3.db

import android.content.ContentValues
import android.database.Cursor
import com.example.githubuserapp3.data.entity.Favorite
import com.example.githubuserapp3.db.DatabaseContract.NoteColumns.Companion.AVATAR
import com.example.githubuserapp3.db.DatabaseContract.NoteColumns.Companion.TYPE
import com.example.githubuserapp3.db.DatabaseContract.NoteColumns.Companion.USERNAME
import com.example.githubuserapp3.db.DatabaseContract.NoteColumns.Companion.USER_ID

object MappingHelper {

    fun mapCursorToArrayList(favoriteCursor: Cursor?): ArrayList<Favorite> {
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

    fun ContentValues.toFavorite(): Favorite =
        Favorite(
            userId = getAsInteger(USER_ID),
            username = getAsString(USERNAME),
            type = getAsString(TYPE),
            avatar = getAsString(AVATAR)
        )

}