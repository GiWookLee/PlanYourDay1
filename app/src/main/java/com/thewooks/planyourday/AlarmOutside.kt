//package com.thewooks.planyourday
//
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.app.PendingIntent
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.os.Build
//import android.os.Vibrator
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.NotificationCompat
//import androidx.core.app.NotificationManagerCompat
//
//class AlarmOutside : BroadcastReceiver() {
//    override fun onReceive(context: Context?, intent: Intent?) {
//
//
//    }
//}
//
//
//class AlarmOutsidesource : AppCompatActivity() {
//    private fun createNotificationChannel(
//        context: Context, importance: Int, showBadge: Boolean,
//        name: String, description: String
//    ) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channelId = "${context.packageName}-$name"
//            val channel = NotificationChannel(channelId, name, importance)
//            channel.description = description
//            channel.setShowBadge(showBadge)
//
//            val notificationManager = context.getSystemService(NotificationManager::class.java)
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
//
//    fun headUp() {
//        clearExistingNotifications(MainActivity.NOTIFICATION_ID)
//        createNotificationChannel(
//            this, NotificationManagerCompat.IMPORTANCE_HIGH, false,
//            getString(R.string.app_name), "App notification channel"
//        )
//
//        val channelId = "$packageName-${getString(R.string.app_name)}"
//        val title = "Plan Your Day Alarm"
//        val content = "Time to plan your day!"
//
//        val intent = Intent(baseContext, Plan_your_day_click::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        val fullScreenPendingIntent = PendingIntent.getActivity(
//            baseContext, 0,
//            intent, PendingIntent.FLAG_UPDATE_CURRENT
//        )
//
//        val builder = NotificationCompat.Builder(this, channelId)
//        builder.setSmallIcon(R.drawable.transparency)
//        builder.setContentTitle(title)
//        builder.setContentText(content)
//        builder.priority = NotificationCompat.PRIORITY_HIGH
//        builder.setAutoCancel(true)
//        builder.setFullScreenIntent(fullScreenPendingIntent, true)
//
//        val notificationManager = NotificationManagerCompat.from(this)
//        notificationManager.notify(MainActivity.NOTIFICATION_ID_2, builder.build())
//
//    }
//
//    fun basic() {
//        vibrator()
//        clearExistingNotifications(MainActivity.NOTIFICATION_ID)
//        clearExistingNotifications(MainActivity.NOTIFICATION_ID_2)
//        createNotificationChannel(this, NotificationManagerCompat.IMPORTANCE_DEFAULT, false,
//            getString(R.string.app_name), "App notification channel")
//
//        val channelId = "$packageName-${getString(R.string.app_name)}"
//        val title = "Plan Your Day Alarm"
//        val content = "Time to plan your day!"
//
//        val intent = Intent(baseContext, Plan_your_day_click::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        val pendingIntent = PendingIntent.getActivity(baseContext, 0,
//            intent, PendingIntent.FLAG_UPDATE_CURRENT)
//
//        val builder = NotificationCompat.Builder(this, channelId)
//        builder.setSmallIcon(R.drawable.transparency)
//        builder.setContentTitle(title)
//        builder.setContentText(content)
//        builder.priority = NotificationCompat.PRIORITY_DEFAULT
//        builder.setAutoCancel(true)
//        builder.setContentIntent(pendingIntent)
//
//        val notificationManager = NotificationManagerCompat.from(this)
//        notificationManager.notify(MainActivity.NOTIFICATION_ID, builder.build())
//    }
//
//
//    private fun clearExistingNotifications(notificationId: Int) {
//        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        manager.cancel(notificationId)
//    }
//
//    fun vibrator(){
//        val vibrator : Vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//        val array: LongArray = longArrayOf(1000, 50, 1000, 50,1000,50)
//        vibrator.vibrate(array, -1)
//    }
//}