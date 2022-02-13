package com.example.medrem

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Vibrator
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.ActivityNavigator
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val sharedPref = context.getSharedPreferences("AlarmSet", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val oldrecieved = sharedPref.getInt("RECIEVED",0)
        editor.apply{
            putInt("RECIEVED",oldrecieved+1)
            apply()
        }

        val newrecieved = sharedPref.getInt("RECIEVED",0)

        val med_name = sharedPref.getString(newrecieved.toString()+"MedName",null)
        val med_type = sharedPref.getString(newrecieved.toString()+"MedType",null)

        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        //Notification ID
        val channelid="MEDREM"
        val manger=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //check for device only available for Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel= NotificationChannel(channelid,"alarm notification", NotificationManager.IMPORTANCE_HIGH)
            channel.enableLights(true)
            manger.createNotificationChannel(channel)
        }
        //build notification

        val builder = NotificationCompat.Builder(context!!, channelid)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("MEDREM Reminder")
            .setContentText("Its time to take your $med_name $med_type  \nMark it as Done in the App when you are done. ")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        //Deliver notification
        manger.notify(0,builder.build())
        vibrator.vibrate(300)

    }
}