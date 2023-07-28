package com.example.repository

import com.example.dao.ContactsDisplay
import com.example.dao.ContactsStore

interface Contacts {
    suspend fun storeContacts(input:ContactsStore):Unit
    suspend fun displayUserContacts(userName:String):List<ContactsDisplay>
}