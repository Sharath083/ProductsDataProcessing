package com.example.plugins

import com.example.logic.DaoQueryImpl
import com.example.logic.Methods
import com.example.routes.dataProcessing
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.http.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {

        dataProcessing(DaoQueryImpl(),Methods())
    }
}
