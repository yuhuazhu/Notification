package com.yhz.notification

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.widget.Button

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(Button(this).apply {
            text = "发送通知"
            setOnClickListener { push() }
        })
    }

    private fun push() {
        val intent = Intent(this, MainActivity::class.java)
        val pIntent = PendingIntent.getActivity(this, 1, intent, 1)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel("1", "yhz", NotificationManager.IMPORTANCE_HIGH + 1).apply {
                enableLights(true)// 呼吸灯
                enableVibration(true)// 震动
                setShowBadge(true)// 角标
                manager.createNotificationChannel(this)
            }
        }
        val builder = NotificationCompat.Builder(this, "1")
        with(builder) {
            setSmallIcon(R.mipmap.ic_notification)
            setContentTitle("告诉你个好消息～")
            setContentText("我会背乘法口诀啦！！！")
            setContentIntent(pIntent)
            priority = NotificationManagerCompat.IMPORTANCE_MAX
            setDefaults(NotificationCompat.DEFAULT_ALL)
            setAutoCancel(true)
            builder.setChannelId("1")
        }
        val notify = builder.build()
        manager.notify(1, notify)
    }
}
