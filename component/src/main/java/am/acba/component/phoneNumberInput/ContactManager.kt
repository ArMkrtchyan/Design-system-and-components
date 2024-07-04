package am.acba.component.phoneNumberInput

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object ContactManager {
    @SuppressLint("Range")
    suspend fun getContactFromUri(uri: Uri, context: Context): Flow<Contact> {
        return flow {
            var contact = Contact()
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            val cursorContacts = context.contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
            cursor?.moveToFirst()
            val selectedContactId = cursor?.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
            cursor?.close()
            while (cursorContacts?.moveToNext() == true) {
                val contactId = cursorContacts.getString(cursorContacts.getColumnIndex(ContactsContract.Contacts._ID))
                if (contactId == selectedContactId) {
                    val contactName = cursorContacts.getString(cursorContacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    contact = contact.copy(name = contactName)
                    val phoneNumberCursor =
                        context.contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
                            arrayOf(selectedContactId),
                            null
                        )
                    while (phoneNumberCursor?.moveToNext() == true) {
                        val phoneNumber = phoneNumberCursor.getString(phoneNumberCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        contact = contact.copy(phoneNumber = validateContactNumber(phoneNumber))
                    }
                    val emailCursor =
                        context.contentResolver.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            "${ContactsContract.CommonDataKinds.Email.CONTACT_ID} = ?",
                            arrayOf(selectedContactId),
                            null
                        )
                    while (emailCursor?.moveToNext() == true) {
                        val email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))
                        contact = contact.copy(email = email)
                    }
                    emailCursor?.close()
                    phoneNumberCursor?.close()
                    break
                }
            }
            cursorContacts?.close()
            emit(contact)
        }.flowOn(Dispatchers.Default)
    }

    private fun validateContactNumber(phoneNo: String?): String? {
        var phoneNumber = phoneNo?.replace("\\s+".toRegex(), "")?.replace("-", "")?.trim { it <= ' ' }
        if (phoneNumber?.startsWith("0") == true) {
            phoneNumber = phoneNumber.substring(1)
        }
        return if (phoneNumber?.length == 9 || phoneNumber?.length == 8) {
            "+374$phoneNumber"
        } else {
            phoneNumber
        }
    }
}