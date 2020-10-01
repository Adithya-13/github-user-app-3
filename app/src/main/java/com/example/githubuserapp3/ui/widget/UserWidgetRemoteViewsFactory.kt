package com.example.githubuserapp3.ui.widget

import android.content.Context
import android.database.Cursor
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuserapp3.R
import com.example.githubuserapp3.data.entity.Favorite
import com.example.githubuserapp3.db.DatabaseContract.NoteColumns.Companion.CONTENT_URI
import com.example.githubuserapp3.db.MappingHelper

internal class UserWidgetRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private var list = ArrayList<Favorite>()
    private var cursor: Cursor? = null

    override fun onCreate() {}

    override fun onDataSetChanged() {
        val identityToken = Binder.clearCallingIdentity()
        val cursor = mContext.contentResolver?.query(
            CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val item = MappingHelper.mapCursorToArrayList(cursor)
        list.clear()
        list = item

        Binder.restoreCallingIdentity(identityToken)
    }

    override fun onDestroy() {
        cursor?.close()
        list = arrayListOf()
    }

    override fun getCount(): Int = list.size

    override fun getViewAt(position: Int): RemoteViews {

        val views = RemoteViews(mContext.packageName, R.layout.list_items_widget)

        return try {
            val bitmap = Glide.with(mContext)
                .asBitmap()
                .load(list[position].avatar)
                .apply(RequestOptions().centerCrop())
                .submit(800, 550)
                .get()
            views.setImageViewBitmap(R.id.userAvatarWidget, bitmap)
            views.setTextViewText(R.id.tvUsernameWidget, list[position].username)
            views.setTextViewText(R.id.tvTypeWidget, list[position].type)

            views
        } catch (e: Exception) {
            views
        }

    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false

    override fun getViewTypeCount(): Int = 1

}