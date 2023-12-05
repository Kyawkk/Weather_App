package com.kyawzinlinn.core_navigation

import android.content.Context
import android.content.Intent
import androidx.core.app.TaskStackBuilder

fun exitApp(context: Context) {
    // Create an Intent to start the launcher Activity
    val intent = Intent(Intent.ACTION_MAIN).apply {
        addCategory(Intent.CATEGORY_HOME)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }

    // Use TaskStackBuilder to bring the launcher Activity to the front and finish all other activities
    val taskStackBuilder = TaskStackBuilder.create(context)
    taskStackBuilder.addNextIntentWithParentStack(intent)
    taskStackBuilder.startActivities()
}