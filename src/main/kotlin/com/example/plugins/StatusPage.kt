package com.example.plugins

import com.example.data.request.RequestQuery
import com.example.data.request.UpdateProperties
import com.example.data.response.Output
import com.example.exception.InvalidRequestType
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*


fun Application.configureStatusPage() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
        exception<RequestValidationException> { call, cause ->
            call.respondText(text="$cause ${cause.reasons.joinToString()}",status=HttpStatusCode.BadRequest,)
        }
        exception<InvalidRequestType> { call, cause ->
            call.respond(Output("$cause ${cause.msg}",HttpStatusCode.InternalServerError.toString()))
        }
    }
}
fun Application.configRequestValidation() {
    install(RequestValidation) {
        validate<RequestQuery> {
            if (it.query == "" || it.query == null ) {
                throw InvalidRequestType("Give Proper Input")
            } else {
                ValidationResult.Valid
            }
        }
        validate<UpdateProperties> {
            if ( it.id == null ) {
                throw InvalidRequestType("Give Proper Input")
            } else {
                ValidationResult.Valid
            }
        }
    }

}
