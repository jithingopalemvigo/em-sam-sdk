package com.begini.androidsdkv2.workerutil

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.CalendarContract
import android.util.Log
import com.begini.androidsdkv2.model.devicedata.CalenderAttendees
import com.begini.androidsdkv2.model.devicedata.CalenderEventModel
import com.begini.androidsdkv2.model.devicedata.CalenderInfoModel
import com.begini.androidsdkv2.model.devicedata.CalenderInstanceModel

class CalanderUtil {

    @SuppressLint("Range")
    fun readCalendarEvents(contentResolver: ContentResolver): MutableList<CalenderEventModel> {
        var calendarEventList: MutableList<CalenderEventModel> = arrayListOf()

        val projection = arrayOf(
            CalendarContract.Events.GUESTS_CAN_MODIFY,
            CalendarContract.Events._ID,
            CalendarContract.Events.RRULE,
            CalendarContract.Events.ALL_DAY,
            CalendarContract.Events.EVENT_TIMEZONE,
            CalendarContract.Events.ORGANIZER,
            CalendarContract.Events.AVAILABILITY,
            CalendarContract.Events.EVENT_COLOR,
            CalendarContract.Events.GUESTS_CAN_INVITE_OTHERS,
            CalendarContract.Events.TITLE,
            CalendarContract.Events.CALENDAR_ID,
            CalendarContract.Events.EVENT_LOCATION,
            CalendarContract.Events.ACCESS_LEVEL,
            CalendarContract.Events.HAS_ALARM,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.EXDATE,
            CalendarContract.Events.DESCRIPTION,
            CalendarContract.Events.DTEND,
            CalendarContract.Events.EVENT_END_TIMEZONE,
            CalendarContract.Events.RDATE,
            CalendarContract.Events.EXRULE,
            CalendarContract.Events.GUESTS_CAN_SEE_GUESTS,
            CalendarContract.Events.HAS_EXTENDED_PROPERTIES,
            CalendarContract.Events.DESCRIPTION,
            CalendarContract.Events.CALENDAR_DISPLAY_NAME,
            CalendarContract.Events.ORIGINAL_ID,


            )
        val projectionInstances = arrayOf(
            CalendarContract.Instances.BEGIN,
            CalendarContract.Instances.END_MINUTE,
            CalendarContract.Instances.START_MINUTE,
            CalendarContract.Instances.START_DAY,
            CalendarContract.Instances.END_DAY,
            CalendarContract.Instances.END,
            CalendarContract.Instances.EVENT_ID,
            CalendarContract.Instances._ID,

            )
        val projectionAttendees = arrayOf(
            CalendarContract.Attendees.ATTENDEE_NAME,
            CalendarContract.Attendees.ATTENDEE_EMAIL,
        )
        val selection = CalendarContract.Events.DELETED + " != 1"

        val cursor = contentResolver.query(
            CalendarContract.Events.CONTENT_URI,
            projection, selection, null, null
        )

        if (cursor != null) {
            cursor.moveToFirst()
            var i = 0
            while (cursor.moveToNext()) {
                var id = cursor.getString(
                    cursor!!.getColumnIndex(
                        CalendarContract.Events._ID
                    )
                )
                var startTime = cursor.getString(
                    cursor!!.getColumnIndex(
                        CalendarContract.Events.DTSTART
                    )
                )
                var endTime = cursor.getString(
                    cursor!!.getColumnIndex(
                        CalendarContract.Events.DTEND
                    )
                )

                val selectionInstance = if (id != null)
                    CalendarContract.Instances.BEGIN + " > " + startTime + " and " + CalendarContract.Instances.END + " > " + endTime else null
                val selectionAttendees = if (id != null)
                    CalendarContract.Attendees.EVENT_ID + " == " + id else null
                val cursorAttendees = contentResolver.query(
                    CalendarContract.Attendees.CONTENT_URI,
                    projectionAttendees, selectionAttendees, null, null
                )
                val cursorInstance = CalendarContract.Instances.query(contentResolver,
                    projectionInstances, startTime.toLong(),endTime.toLong()
                )

                var instances: CalenderInstanceModel? = null
                var eventAttendees = arrayListOf<CalenderAttendees>()
                var eventInstances = arrayListOf<CalenderInstanceModel>()
                if (cursorAttendees != null) {
                    cursorAttendees.moveToFirst()
                    while (cursorAttendees.moveToNext()) {
                        eventAttendees.add(
                            CalenderAttendees(
                                attendeeName = cursorAttendees.getString(
                                    cursorAttendees!!.getColumnIndex(
                                        CalendarContract.Attendees.ATTENDEE_NAME
                                    )
                                ),
                                email = cursorAttendees.getString(
                                    cursorAttendees!!.getColumnIndex(
                                        CalendarContract.Attendees.ATTENDEE_EMAIL
                                    )
                                ),
                            )
                        )
                    }
                    cursorAttendees.close()
                }
                if (cursorInstance != null) {
                    cursorInstance.moveToFirst()
                    while (!cursorInstance.isAfterLast()) {
                        instances = CalenderInstanceModel(
                            id = cursorInstance.getString(
                                cursorInstance!!.getColumnIndex(
                                    CalendarContract.Instances._ID
                                )
                            ),
                            begin = cursorInstance.getString(
                                cursorInstance!!.getColumnIndex(
                                    CalendarContract.Instances.BEGIN
                                )
                            ),
                            end = cursorInstance.getString(
                                cursorInstance!!.getColumnIndex(
                                    CalendarContract.Instances.END
                                )
                            ),
                            endDay = cursorInstance.getString(
                                cursorInstance!!.getColumnIndex(
                                    CalendarContract.Instances.END_DAY
                                )
                            ),
                            endMinute = cursorInstance.getString(
                                cursorInstance!!.getColumnIndex(
                                    CalendarContract.Instances.END_MINUTE
                                )
                            ),
                            startDay = cursorInstance.getString(
                                cursorInstance!!.getColumnIndex(
                                    CalendarContract.Instances.START_DAY
                                )
                            ),
                            startMinute = cursorInstance.getString(
                                cursorInstance!!.getColumnIndex(
                                    CalendarContract.Instances.START_MINUTE
                                )
                            ),
                        )
                        eventInstances.add(instances)
                        cursorInstance.moveToNext()
                    }
                    cursorInstance.close()
                }

                var calanderInfoModel = CalenderInfoModel(
                    id = cursor.getString(
                        cursor!!.getColumnIndex(
                            CalendarContract.Events.CALENDAR_ID
                        )
                    ), displayName = cursor.getString(
                        cursor!!.getColumnIndex(
                            CalendarContract.Events.CALENDAR_DISPLAY_NAME
                        )
                    )
                )
                val event = CalenderEventModel(

                    guestsCanModify = cursor.getString(
                        cursor!!.getColumnIndex(
                            CalendarContract.Events.GUESTS_CAN_MODIFY
                        )
                    ),
                    rRule = cursor.getString(cursor!!.getColumnIndex(CalendarContract.Events.RRULE)),
                    isAllDay = cursor.getString(
                        cursor!!.getColumnIndex(
                            CalendarContract.Events.ALL_DAY
                        )
                    ),
                    timezone = cursor.getString(
                        cursor!!.getColumnIndex(
                            CalendarContract.Events.EVENT_TIMEZONE
                        )
                    ),
                    organizer = cursor.getString(
                        cursor!!.getColumnIndex(
                            CalendarContract.Events.ORGANIZER
                        )
                    ),
                    availability = cursor.getString(
                        cursor!!.getColumnIndex(
                            CalendarContract.Events.AVAILABILITY
                        )
                    ),
                    eventColor = cursor.getString(
                        cursor!!.getColumnIndex(
                            CalendarContract.Events.EVENT_COLOR
                        )
                    ),
                    guestsCanInviteOthers = cursor.getString(
                        cursor!!.getColumnIndex(
                            CalendarContract.Events.GUESTS_CAN_INVITE_OTHERS
                        )
                    ),
                    title = cursor.getString(cursor!!.getColumnIndex(CalendarContract.Events.TITLE)),
                    id = cursor.getString(
                        cursor!!.getColumnIndex(
                            CalendarContract.Events.ORIGINAL_ID
                        )
                    ),
                    accessLevel = cursor.getString(
                        cursor!!.getColumnIndex(
                            CalendarContract.Events.ACCESS_LEVEL
                        )
                    ),
                    location = cursor.getString(
                        cursor!!.getColumnIndex(
                            CalendarContract.Events.EVENT_LOCATION
                        )
                    ),
                    hasAlarm = cursor.getString(
                        cursor!!.getColumnIndex(
                            CalendarContract.Events.HAS_ALARM
                        )
                    ),
                    startDate = cursor.getString(
                        cursor!!.getColumnIndex(
                            CalendarContract.Events.DTSTART
                        )
                    ),
                    exDate = cursor.getString(
                        cursor!!.getColumnIndex(
                            CalendarContract.Events.EXDATE
                        )
                    ),
                    description = cursor.getString(
                        cursor!!.getColumnIndex(
                            CalendarContract.Events.DESCRIPTION
                        )
                    ),
                    endDate = cursor.getString(
                        cursor!!.getColumnIndex(
                            CalendarContract.Events.DTEND
                        )
                    ),
                    endTimezone = cursor.getString(
                        cursor!!.getColumnIndex(
                            CalendarContract.Events.EVENT_END_TIMEZONE
                        )
                    ),
                    rDate = cursor.getString(
                        cursor!!.getColumnIndex(
                            CalendarContract.Events.RDATE
                        )
                    ),
                    guestsCanSeeGuests = cursor.getString(
                        cursor!!.getColumnIndex(
                            CalendarContract.Events.GUESTS_CAN_SEE_GUESTS
                        )
                    ),
                    exRule = cursor.getString(
                        cursor!!.getColumnIndex(
                            CalendarContract.Events.EXRULE
                        )
                    ),
                    hasExtendedProperties = cursor.getString(
                        cursor!!.getColumnIndex(
                            CalendarContract.Events.HAS_EXTENDED_PROPERTIES
                        )
                    ),
                    calendar = calanderInfoModel,
                    instances = eventInstances,
                    attendees =
                    eventAttendees
                )
                calendarEventList.add(event)
                i++
                if (i == 21) break
            }
            cursor.close()
        }
        return calendarEventList
    }
}