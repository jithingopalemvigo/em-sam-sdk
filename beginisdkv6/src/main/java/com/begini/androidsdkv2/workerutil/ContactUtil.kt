package com.begini.androidsdkv2.workerutil

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.ContactsContract
import com.begini.androidsdkv2.model.devicedata.ContactModel
import com.begini.androidsdkv2.model.devicedata.PhoneNumber


class ContactUtil {

    /* @param contentResolver : Is used to get the content resolver object from current activity.
    *  @return ArrayList<ContactModel>: returns the list of available contact names and phone number.
    *  Please note if the number is not available for a name, that contact is not added to the list which returns.
    * */
    @SuppressLint("Range")
    fun getContacts(contentResolver: ContentResolver): ArrayList<ContactModel> {
        var contactList = arrayListOf<ContactModel>()
        var cursorContact = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI, arrayOf(
                ContactsContract.Contacts._ID,//id need for request phone number of the contact
                ContactsContract.Contacts.HAS_PHONE_NUMBER,//flag to identify the contact have phone number or not
                ContactsContract.Contacts.DISPLAY_NAME, //get the contact name
                ContactsContract.Contacts.STARRED,
                ContactsContract.Contacts.LOOKUP_KEY,
                ContactsContract.Contacts.CUSTOM_RINGTONE,
                ContactsContract.Contacts.SEND_TO_VOICEMAIL,
                ContactsContract.Contacts.IN_VISIBLE_GROUP,
                ContactsContract.Contacts.IS_USER_PROFILE,
                ContactsContract.Contacts.PHOTO_ID,
                ContactsContract.Contacts.CONTACT_STATUS,
                ContactsContract.Contacts.CONTACT_LAST_UPDATED_TIMESTAMP,
                ContactsContract.Contacts.CONTACT_STATUS_TIMESTAMP,
//                ContactsContract.Contacts.Data.DATA1
            ), null, null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        )
        // data is a array of String type which is
        // used to store Number ,Names and id.
        val data = arrayOf(
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.STARRED,
            ContactsContract.CommonDataKinds.Phone.TYPE,
        )
        if (cursorContact != null && cursorContact.count > 0) {
            cursorContact.moveToFirst()
            var i=0
            while (!cursorContact.isAfterLast) {
                val id: String? = cursorContact.getString(
                    cursorContact.getColumnIndex(ContactsContract.Contacts._ID)
                )
                val name: String? = cursorContact.getString(
                    cursorContact.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME
                    )
                )
                val isStarredContact: String? = cursorContact.getString(
                    cursorContact.getColumnIndex(
                        ContactsContract.Contacts.STARRED
                    )
                )
                val lookUpKey: String? = cursorContact.getString(
                    cursorContact.getColumnIndex(
                        ContactsContract.Contacts.LOOKUP_KEY
                    )
                )
                val hasCustomRingtne: String? = cursorContact.getString(
                    cursorContact.getColumnIndex(
                        ContactsContract.Contacts.CUSTOM_RINGTONE
                    )
                )
                val sendToVoiceMail: String? = cursorContact.getString(
                    cursorContact.getColumnIndex(
                        ContactsContract.Contacts.SEND_TO_VOICEMAIL
                    )
                )
                val inVisibleGroup: String? = cursorContact.getString(
                    cursorContact.getColumnIndex(
                        ContactsContract.Contacts.IN_VISIBLE_GROUP
                    )
                )
                val isUserProfile: String? = cursorContact.getString(
                    cursorContact.getColumnIndex(
                        ContactsContract.Contacts.IS_USER_PROFILE
                    )
                )
                val hasPhotoId: String? = cursorContact.getString(
                    cursorContact.getColumnIndex(
                        ContactsContract.Contacts.PHOTO_ID
                    )
                )
                val hasContactStatus: String? =""
                    /* cursorContact.getString(
                    cursorContact.getColumnIndex(
                        ContactsContract.Contacts.CONTACT_STATUS
                    )
                )*/
                val contactStatusTimestamp: String? = cursorContact.getString(
                    cursorContact.getColumnIndex(
                        ContactsContract.Contacts.CONTACT_STATUS_TIMESTAMP
                    )
                )
                val lastUpdatedTimeStamp: String? = cursorContact.getString(
                    cursorContact.getColumnIndex(
                        ContactsContract.Contacts.CONTACT_LAST_UPDATED_TIMESTAMP
                    )
                )
                val email: String ="" /*cursorContact.getString(
                    cursorContact.getColumnIndex(
                        ContactsContract.Contacts.Data.DATA1
                    )
                )*/
                if (cursorContact.getInt(cursorContact.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    var cursorPhone = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        data,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                        null,
                        null
                    )
                    if (cursorPhone != null) {
                        cursorPhone.moveToFirst()
                        while (!cursorPhone.isAfterLast) {
                            var number =
                                cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            var isStarredPhoneNumber =
                                cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.STARRED));
                            var type =
                                cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                             contactList.add(
                                ContactModel(
                                    id,
                                    name,
                                    isStarredContact,
                                    lookUpKey,
                                    hasCustomRingtne,
                                    sendToVoiceMail,
                                    inVisibleGroup,
                                    isUserProfile,
                                    hasPhotoId,
                                    hasContactStatus,
                                    contactStatusTimestamp,
                                    lastUpdatedTimeStamp,email,
                                    PhoneNumber(number, isStarredPhoneNumber, type)
                                )
                            )
                            cursorPhone.moveToNext()
                        }
                        cursorPhone.close()
                    }
                }
                cursorContact.moveToNext()
                i++
//if(i==20) break
            }
            cursorContact.close()
        } else {
            //
        }
        return contactList;
    }
}