package com.example.routes

import com.example.dao.ContactsStore
import com.example.data.request.RequestQuery
import com.example.repository.ContactsImpl
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.contactsRoute(contactsImpl: ContactsImpl){
    route("/contact"){
        post("/addContact"){
            val data=call.receive<ContactsStore>()
            contactsImpl.storeContacts(data)
            call.respond("Stored")
        }
        get("/storedContacts"){
            val user=call.receive<RequestQuery>()
            val list=contactsImpl.displayUserContacts(user.query!!)
            call.respond(list)
        }
    }
}