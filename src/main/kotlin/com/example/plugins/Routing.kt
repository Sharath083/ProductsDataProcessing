package com.example.plugins

import com.example.repository.ContactsImpl
import com.example.repository.DaoQueryImpl
import com.example.repository.Methods
import com.example.routes.contactsRoute
import com.example.routes.dataProcessing
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {

        dataProcessing(DaoQueryImpl(),Methods())
        contactsRoute(ContactsImpl())
    }
}
