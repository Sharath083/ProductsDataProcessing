package com.example.routes

import com.example.data.request.RequestQuery
import com.example.logic.DaoQueryImpl
import com.example.logic.Methods
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.dataProcessing(daoQueryImpl: DaoQueryImpl, methods: Methods){
    route("/data"){
        get("/insertData") {
            val url="https://dummyjson.com/products"
            val list=methods.propertiesList(url)
            daoQueryImpl.insertIntoTable(list!!)
            call.respond(" Inserted")
        }
        delete("/deleteById") {
            val productId=call.receive<RequestQuery>()
            val output=daoQueryImpl.deleteProductById(productId.query)
            call.respond(output)


        }
    }
}