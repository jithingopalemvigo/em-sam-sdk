package com.begini.androidsdk.model

import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.begini.androidsdkv2.R

fun makeStatusNotification(message: String, context: Context) {

    // Make a channel if necessary
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
        val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = description

        // Add the channel
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        notificationManager?.createNotificationChannel(channel)
    }

    // Create the notification
    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(NOTIFICATION_TITLE)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))

    // Show the notification
    NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
}

// Notification Channel constants

// Name of Notification Channel for verbose notifications of background work
@JvmField
val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
    "Begini Notifications"
const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
    "Shows notifications whenever work starts"

@JvmField
val NOTIFICATION_TITLE: CharSequence = "WorkRequest Starting"
const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
const val NOTIFICATION_ID = 98

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(applicationContext, message, duration).show()
}

fun Context.isPermissionAllowed(permission: String): Boolean {
    return (ActivityCompat.checkSelfPermission(this, permission)
            == PackageManager.PERMISSION_GRANTED)
}

fun Activity.showDialogMessage(
    title: String?=null,
    message: String,
    positiveButtonName: String?,
    negativeButtonName: String?=null,
    positiveButtonClickListener: DialogInterface.OnClickListener?,
    negativeButtonClickListener: DialogInterface.OnClickListener?=null,
    isCancelable:Boolean=true

    ) {
    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
    builder.setMessage(message)
    builder.setCancelable(isCancelable)
    if (title != null) builder.setTitle(title)
    if (positiveButtonName != null) builder.setPositiveButton(
        positiveButtonName,
        positiveButtonClickListener
    )
    if (negativeButtonName != null) builder.setNegativeButton(
        negativeButtonName,
        negativeButtonClickListener
    )
    builder.show()
}
fun AppCompatActivity.replaceFragment(
    fragment: Fragment,
    tag: String,
    @IdRes containerViewId: Int,
) {
    val ft = supportFragmentManager
    val f = findFragmentByTag(tag)
    if (f == null) {
        ft.beginTransaction()
            .replace(containerViewId, fragment, tag).commit()
    } else {
        ft.beginTransaction()
            .replace(containerViewId, f, tag).commit()
    }
}
/**
 * Method to get fragment by tag. The operation is performed by the supportFragmentManager.
 */
fun AppCompatActivity.findFragmentByTag(tag: String): Fragment? {
    return supportFragmentManager.findFragmentByTag(tag)
}
