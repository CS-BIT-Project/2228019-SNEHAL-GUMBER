package com.example.gurudev

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.media3.common.util.NotificationUtil.createNotificationChannel
import com.google.android.material.snackbar.Snackbar
import java.util.*

class Lifestyle : Fragment() {
    private val CHANNEL_ID = "lifestyle_channel"
    private lateinit var notificationManager: NotificationManager
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lifestyle, container, false)

        // Initialize NotificationManager & SharedPreferences
        notificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        sharedPreferences = requireActivity().getSharedPreferences("LifestylePrefs", Context.MODE_PRIVATE)
        val btnTestNotification: Button = view.findViewById(R.id.btnTestNotification)
        btnTestNotification.setOnClickListener {
            sendTestNotification()
        }
        createNotificationChannel()
        requestNotificationPermission()

        val switchGetStarted: Switch = view.findViewById(R.id.switchGetStarted)

        // Load saved switch state
        val isSwitchOn = sharedPreferences.getBoolean("switch_state", false)
        switchGetStarted.isChecked = isSwitchOn

        // Handle switch toggle
        switchGetStarted.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("switch_state", isChecked).apply() // Save state

            if (isChecked) {
                Snackbar.make(view, "Timely Notifications Enabled!", Snackbar.LENGTH_SHORT).show()
                scheduleNotifications()
            } else {
                Snackbar.make(view, "Notifications Disabled", Snackbar.LENGTH_SHORT).show()
                cancelAllNotifications()
            }
        }

        return view
    }
    private fun sendTestNotification() {
        // Check for notification permission
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            return // Exit if permission is not granted
        }

        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.hand_with_flaming_torch_vector_image_on_vectorstock_removebg_preview)
            .setContentTitle("Test Notification")
            .setContentText("If you see this, notifications are working!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(requireContext())) {
            notify(1001, builder.build()) // Send the notification
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Lifestyle Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun scheduleNotifications() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            Snackbar.make(requireView(), "Notification permission required!", Snackbar.LENGTH_LONG).show()
            return
        }

        data class NotificationSchedule(val hour: Int, val minute: Int, val title: String, val message: String)

        val schedule = listOf(
            NotificationSchedule(4, 0, "Brahmamuhurta", "Start your day with peace 🌅"),
            NotificationSchedule(5, 30, "Gayatri Upasana", "Time for meditation & mantra chanting 🧘‍♂️"),
            NotificationSchedule(6, 30, "Swadhyaya", "Self-study session 📖"),
            NotificationSchedule(7, 0, "Physical Health", "Yoga and exercise time! 🏋️‍♂️"),
            NotificationSchedule(8, 0, "Seva & Service", "Karma Yoga – Serve selflessly 💖"),
            NotificationSchedule(9, 0, "Work & Livelihood", "Begin your work with mindfulness 💼"),
            NotificationSchedule(12, 0, "Sattvic Diet", "Mindful eating – Nourish your body 🍎"),
            NotificationSchedule(20, 0, "TatvaBodh", "Self-reflection time 🤔"),
            NotificationSchedule(21, 30, "Early Sleep", "Prepare for a restful sleep 😴")
        )

        for (entry in schedule) {
            setAlarm(entry.hour, entry.minute, entry.title, entry.message)
        }
    }


    private fun setAlarm(hour: Int, minute: Int, title: String, message: String) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), NotificationReceiver::class.java).apply {
            putExtra("title", title)
            putExtra("message", message)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            hour * 60 + minute,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    private fun cancelAllNotifications() {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val schedule = listOf(4, 5, 6, 7, 8, 9, 12, 14, 20, 21)
        for (hour in schedule) {
            val intent = Intent(requireContext(), NotificationReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                requireContext(),
                hour * 60,
                intent,
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
            )
            if (pendingIntent != null) {
                alarmManager.cancel(pendingIntent)
            }
        }
    }
}
