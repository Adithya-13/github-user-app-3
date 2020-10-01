package com.example.githubuserapp3.db

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubuserapp3.data.entity.Favorite

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: Favorite): Long

    @Query("DELETE FROM favorite_table WHERE userId = :userId")
    fun deleteUser(userId: Int): Int

    @Query("SELECT * FROM favorite_table ORDER BY username ASC")
    fun getUsers(): Cursor

    @Query("SELECT * FROM favorite_table WHERE userId = :userId")
    fun getUserById(userId: Int): Cursor

    @Query("DELETE FROM favorite_table")
    suspend fun deleteAll()
}