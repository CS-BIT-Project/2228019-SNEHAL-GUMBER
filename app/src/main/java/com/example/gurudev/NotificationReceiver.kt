package com.example.gurudev
import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Reminder"
        val message = intent.getStringExtra("message") ?: "Time for your scheduled activity!"
        val sharedPreferences = context.getSharedPreferences("LifestylePrefs", Context.MODE_PRIVATE)
        val completedTasks = sharedPreferences.getInt("completed_tasks", 0) + 1
        sharedPreferences.edit().putInt("completed_tasks", completedTasks).apply()

        context.sendBroadcast(Intent("UPDATE_PROGRESS"))

        val notification = NotificationCompat.Builder(context, "lifestyle_channel")
            .setSmallIcon(R.drawable.hand_with_flaming_torch_vector_image_on_vectorstock_removebg_preview) // Make sure you have an icon
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU &&
            context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        NotificationManagerCompat.from(context).notify((System.currentTimeMillis() % 10000).toInt(), notification)
    }
}
