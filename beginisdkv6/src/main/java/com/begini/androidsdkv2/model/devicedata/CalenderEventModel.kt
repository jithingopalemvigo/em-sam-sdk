package com.begini.androidsdkv2.model.devicedata

import com.squareup.moshi.Json

data class CalenderEventModel(
    @Json(name = "guests_can_modify")
    val guestsCanModify: String?,
    @Json(name = "rrule")
    val rRule: String?,
    @Json(name = "is_all_day")
    val isAllDay: String?,
    @Json(name = "timezone")
    val timezone: String?,
    @Json(name = "organizer")
    val organizer: String?,
    @Json(name = "availability")
    val availability: String?,
    @Json(name = "event_color")
    val eventColor: String?,
    @Json(name = "guests_can_invite_others")
    val guestsCanInviteOthers: String?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "access_level")
    val accessLevel: String?,
    @Json(name = "location")
    val location: String?,
    @Json(name = "has_alarm")
    val hasAlarm: String?,
    @Json(name = "start_date")
    val startDate: String?,
    @Json(name = "exdate")
    val exDate: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "end_date")
    val endDate: String?,
    @Json(name = "end_timezone")
    val endTimezone: String?,
    @Json(name = "rdate")
    val rDate: String?,
    @Json(name = "guests_can_see_guests")
    val guestsCanSeeGuests: String?,
    @Json(name = "exrule")
    val exRule: String?,
    @Json(name = "has_extended_properties")
    val hasExtendedProperties: String?,
    @Json(name = "instances")
    val instances: MutableList<CalenderInstanceModel>,
    @Json(name = "calendar")
    val calendar: CalenderInfoModel?,
    @Json(name = "attendees")
    val attendees: MutableList<CalenderAttendees>?

)

data class CalenderInfoModel(
    @Json(name = "displayName")
    val displayName: String?,
    @Json(name = "id")
    val id: String?,
)
data class CalenderAttendees(
    @Json(name = "attendee_name")
    val attendeeName: String?,
    @Json(name = "email")
    val email: String?,
)

data class CalenderInstanceModel(
    @Json(name = "begin")
    val begin: String?,
    @Json(name = "end_minute")
    val endMinute: String?,
    @Json(name = "start_minute")
    val startMinute: String?,
    @Json(name = "start_day")
    val startDay: String?,
    @Json(name = "end_day")
    val endDay: String?,
    @Json(name = "end")
    val end: String?,
    @Json(name = "id")
    val id: String?,
)