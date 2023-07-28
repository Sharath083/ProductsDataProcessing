package com.example.dao

import kotlinx.serialization.Serializable

@Serializable
data class ContactsStore(val user:String,val firstName:String,val lastName: String,val mobileNumber:String,val type:String)

@Serializable
data class ContactsDisplay(val firstName:String,val lastName: String,val mobileNumber:String,val type:String)