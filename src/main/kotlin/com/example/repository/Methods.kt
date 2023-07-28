package com.example.repository

//class Methods {
//    fun getUrlData() = runBlocking {
//        val api = "https://dummyjson.com/products"
//        val client = OkHttpClient()
//        val data = async {
//            requestPass(client, api)
//        }
//        data.await()
//    }
//
//    private fun requestPass(client: OkHttpClient, link: String): Response {
//        val request = Request.Builder().url(link).build()
//        return client.newCall(request).execute()
//    }
//}

import com.example.data.response.Properties
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.get
import io.ktor.client.statement.*
import io.ktor.serialization.gson.*
import kotlinx.serialization.Serializable

@Serializable
data class Products(var products: List<Properties>)

class Methods {
    suspend fun propertiesList(url: String): Products? {
        val httpClient = HttpClient {
            install(ContentNegotiation) {
                gson()
            }
        }
        val response = httpClient.get(url)
        return Gson().fromJson(response.bodyAsText(), Products::class.java)
    }
}

