package com.begini.androidsdkv2.workerutil

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.database.Cursor
import android.provider.Telephony
import android.util.Log
import com.begini.androidsdkv2.model.devicedata.SmSDataModel

class SmsUtil {

    /*the function getAllSmsFromProvider will return a list of SMS, we can change the */
    @SuppressLint("Range")
    fun getAllSentSmsFromProvider(contentResolver: ContentResolver): MutableList<SmSDataModel> {
        val lstSms: MutableList<SmSDataModel> = arrayListOf()
        val c: Cursor? = contentResolver.query(
            Telephony.Sms.Sent.CONTENT_URI,
            arrayOf(
                Telephony.Sms.Sent.BODY,
                Telephony.Sms.Sent.ADDRESS,
                Telephony.Sms.Sent.DATE,
                Telephony.Sms.Sent.DATE_SENT,
                Telephony.Sms.Sent.TYPE,
                Telephony.Sms.Sent.SEEN,
                Telephony.Sms.Sent.STATUS,
            ),  // Select body text
            null,
            null,
            Telephony.Sms.Sent.DEFAULT_SORT_ORDER
        ) // Default sort order
        if (c != null) {
            val totalSMS: Int = c.getCount()
            Log.e("SmsManager", "Size: " + totalSMS)

            if (c.moveToFirst()) {
                for (i in 0 until totalSMS) {
                    lstSms.add(
                        SmSDataModel(
                            c.getString(c.getColumnIndex(Telephony.Sms.Sent.BODY)),
                            c.getString(c.getColumnIndex(Telephony.Sms.Sent.ADDRESS)),
                            c.getString(c.getColumnIndex(Telephony.Sms.Sent.DATE)),
                            c.getString(c.getColumnIndex(Telephony.Sms.Sent.DATE_SENT)),
                            c.getString(c.getColumnIndex(Telephony.Sms.Sent.TYPE)),
                            c.getString(c.getColumnIndex(Telephony.Sms.Sent.SEEN)),
                            c.getString(c.getColumnIndex(Telephony.Sms.Sent.STATUS))
                        )
                    )
                    c.moveToNext()
                }
            } else {
               Log.e("SMS UTIL","You have no SMS in Sent")
            }
            c.close()
        }
        return lstSms
    }

    /*the function getAllInboxSmsFromProvider will return a list of SMS, we can change the */
    @SuppressLint("Range")
    fun getAllInBoxSmsFromProvider(contentResolver: ContentResolver): MutableList<SmSDataModel>  {
        val lstSms: MutableList<SmSDataModel> = arrayListOf()
        val c: Cursor? = contentResolver.query(
            Telephony.Sms.Inbox.CONTENT_URI,
            arrayOf(
                Telephony.Sms.Inbox.BODY,
                Telephony.Sms.Inbox.ADDRESS,
                Telephony.Sms.Inbox.DATE,
                Telephony.Sms.Inbox.DATE_SENT,
                Telephony.Sms.Inbox.TYPE,
                Telephony.Sms.Inbox.SEEN,
                Telephony.Sms.Inbox.STATUS,
            ),  // Select body text
            null,
            null,
            Telephony.Sms.Inbox.DEFAULT_SORT_ORDER
        ) // Default sort order
        if (c != null) {
            val totalSMS: Int = c.getCount()

            if (c.moveToFirst()) {
                for (i in 0 until totalSMS) {
                    lstSms.add(
                        SmSDataModel(
                            c.getString(c.getColumnIndex(Telephony.Sms.Inbox.BODY)),
                            c.getString(c.getColumnIndex(Telephony.Sms.Inbox.ADDRESS)),
                            c.getString(c.getColumnIndex(Telephony.Sms.Inbox.DATE)),
                            c.getString(c.getColumnIndex(Telephony.Sms.Inbox.DATE_SENT)),
                            c.getString(c.getColumnIndex(Telephony.Sms.Inbox.TYPE)),
                            c.getString(c.getColumnIndex(Telephony.Sms.Inbox.SEEN)),
                            c.getString(c.getColumnIndex(Telephony.Sms.Inbox.STATUS))
                        )
                    )
                    c.moveToNext()
                }
            } else {
                Log.e("SMS UTIL","You have no SMS in Inbox")
            }
            c.close()
        }
        return lstSms
    }
}