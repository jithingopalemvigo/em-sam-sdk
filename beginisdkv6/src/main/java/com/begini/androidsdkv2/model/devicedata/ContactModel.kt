package com.begini.androidsdkv2.model.devicedata

import com.squareup.moshi.Json

data class ContactModel(
    @Json(name = "id")
    val id: String?,
    @Json(name = "name")
     val name: String?,
    @Json(name = "starred")
    val isStarred: String?,
    @Json(name = "look_up_key")
    val lookupKey: String?,
    @Json(name = "has_custom_ringtone")
    val hasCustomRingtone: String?,
    @Json(name = "send_to_voicemail")
    val sendToVoiceMail: String?,
    @Json(name = "in_visible_group")
    val inVisibleGroup: String?,
    @Json(name = "is_user_profile")
    val isUserProfile: String?,
    @Json(name = "has_photo_id")
    val hasPhotoId: String?,
    @Json(name = "has_contact_status")
    val hasContactStatus: String?,
    @Json(name = "contact_status_timestamp")
    val contactStatusTimestamp: String?,
    @Json(name = "last_updated_timestamp")
    val lastUpdatedTimeStamp: String?,
    @Json(name = "email")
    val email: String?,
    @Json(name = "phone_number")
    val phoneNumber: PhoneNumber
)

data class PhoneNumber(
    @Json(name = "contact_number")
    val contactNumber: String?,
    @Json(name = "is_starred")
    val isStarred: String?,
    @Json(name = "type")
    val type: String?,
)