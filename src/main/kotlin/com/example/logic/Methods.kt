package com.example.logic

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
import kotlinx.coroutines.runBlocking
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

    //fun fetchDataFromLink(link: String): String {
//    try {
//        return httpClient.get<String>(link)
//    } catch (e: Exception) {
//        // Handle exceptions if needed
//        throw e
//    }
//}
//    fun parseProductsFromJson(list: String): List<Properties> {
//        return Json.decodeFromString<List<Properties>>(list)
//    }

}
fun main()= runBlocking {
    println(Methods().propertiesList("https://dummyjson.com/products"))

}

//import io.ktor.client.HttpClient
//import io.ktor.client.engine.apache.Apache
//import io.ktor.client.features.json.JsonFeature
//import io.ktor.client.features.json.serializer.KotlinxSerializer
//import kotlinx.serialization.Serializable
//
//suspend fun propertiesList(url: String): Products? {
//    val httpClient = HttpClient(Apache) {
//        install(JsonFeature) {
//            serializer = KotlinxSerializer()
//        }
//    }
//    try {
//        val response = httpClient.get<String>(url)
//        return kotlinx.serialization.json.Json.decodeFromString(Products.serializer(), response)
//    } catch (e: Exception) {
//        // Handle exceptions here (e.g., log the error)
//        e.printStackTrace()
//    } finally {
//        httpClient.close()
//    }
//    return null
//}
//
//@Serializable
//data class Products(val products: List<Properties>)
//
//@Serializable
//data class Properties(
//    val id: Int,
//    val title: String,
//    val description: String,
//    val price: Int,
//    val discountPercentage: Double,
//    val rating: Double,
//    val stock: Int,
//    val brand: String,
//    val category: String,
//    val thumbnail: String,
//    val images: List<String>
//)
