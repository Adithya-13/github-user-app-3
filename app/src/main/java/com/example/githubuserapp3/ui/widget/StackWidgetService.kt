package com.example.githubuserapp3.ui.widget

import android.content.Intent
import android.widget.RemoteViewsService

class StackWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return UserWidgetRemoteViewsFactory(this.applicationContext)
    }
}
