package com.example.tracklet.classes

import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract

data class Contact(
    val id: String,
    val name: String,
    val phoneNumber: String
)

class ContactManager(private val context: Context) {
    private val contentResolver: ContentResolver = context.contentResolver

    fun readContacts(): List<Contact> {
        val contacts = mutableListOf<Contact>()

        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )

        contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )?.use { cursor ->
            while (cursor.moveToNext()) {
                val id = cursor.getString(0)
                val name = cursor.getString(1)
                val number = cursor.getString(2)
                contacts.add(Contact(id, name, number))
            }
        }

        return contacts
    }


}