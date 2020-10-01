package com.example.consumerapp.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.consumerapp.db.DatabaseContract.NoteColumns.Companion.AVATAR
import com.example.consumerapp.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.example.consumerapp.db.DatabaseContract.NoteColumns.Companion.TYPE
import com.example.consumerapp.db.DatabaseContract.NoteColumns.Companion.USERNAME
import com.example.consumerapp.db.DatabaseContract.NoteColumns.Companion.USER_ID
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME)
data class Favorite(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = USER_ID) val userId: Int,
    @ColumnInfo(name = USERNAME) val username: String,
    @ColumnInfo(name = TYPE) val type: String,
    @ColumnInfo(name = AVATAR) val avatar: String
) : Parcelable