package com.example.medrem

import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.ActivityNavigator
import androidx.navigation.fragment.FragmentNavigator

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val i = Intent(context,ActivityNavigator.Destination::class.java)
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context,0,i,0)
        Toast.makeText(context,"Inside receive function",Toast.LENGTH_SHORT).show()
        val builder = NotificationCompat.Builder(context!!,"MEDREM")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("MEDREM Reminder")
            .setContentText("Its time to take your medicine")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(123,builder.build())

    }
}