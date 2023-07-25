package com.example.plugins

import com.example.data.request.RequestQuery
import com.example.exception.ProductNotFoundException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.apache.hc.core5.http.protocol.RequestValidateHost
import java.lang.NumberFormatException

fun Application.configureStatusPage() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
//        exception<ProductNotFoundException> {call,cause->
//            call.respondText(text = "404: $cause  ${cause.msg}" , status = HttpStatusCode.InternalServerError)
//        }
//        exception<NumberFormatException> {call,cause->
//            call.respondText(text = "404: $cause  Invlid Input" , status = HttpStatusCode.InternalServerError)
//        }
    }
}
fun Application.configRequestValidation() {
    install(RequestValidation) {
        validate<RequestQuery> {
            if (it.query == "" || it.query == null ) {
                ValidationResult.Invalid("Give The Proper Query")
            } else {
                ValidationResult.Valid
            }
        }
    }

}
