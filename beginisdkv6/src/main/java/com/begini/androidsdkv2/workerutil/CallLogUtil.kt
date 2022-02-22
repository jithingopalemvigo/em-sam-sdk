package com.begini.androidsdkv2.workerutil

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.CallLog
import android.util.Log
import com.begini.androidsdkv2.model.devicedata.CallLogModel


class CallLogUtil {
    @SuppressLint("Range")
    fun getCallLog(contentResolver: ContentResolver): MutableList<CallLogModel> {
        var callHistory: MutableList<CallLogModel>
        callHistory = arrayListOf()
// create cursor and query the data
        var cursor = contentResolver.query(
            CallLog.Calls.CONTENT_URI, arrayOf(
                CallLog.Calls._ID,
                CallLog.Calls.NUMBER,
                CallLog.Calls.TYPE,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION
            ), null, null,
            CallLog.Calls.DATE + " ASC"
        )


        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()
            var i=0
            while (!cursor.isAfterLast ) {
                val number: String = cursor.getString(
                    cursor.getColumnIndex(
                        CallLog.Calls.NUMBER
                    )
                )
                val date = cursor.getString(
                    cursor.getColumnIndex(
                        CallLog.Calls.DATE
                    )
                )
                val type = cursor.getString(
                    cursor.getColumnIndex(
                        CallLog.Calls.TYPE
                    )
                )
                val id = cursor.getString(
                    cursor.getColumnIndex(
                        CallLog.Calls._ID
                    )
                )
                val duration = cursor.getString(
                    cursor.getColumnIndex(
                        CallLog.Calls.DURATION
                    )
                )

                callHistory.add(CallLogModel(number, type, date,duration,id))
                cursor.moveToNext()
//if(i++==20 )break
            }
            cursor.close()
        } else {
            Log.e("Call logs", "inside if , cursor   " + cursor)
        }
        return callHistory
    }
}