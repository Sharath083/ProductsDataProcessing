package com.example.repository

import com.example.data.request.UpdateProperties
import com.example.data.response.Output

interface DaoQuery {

    suspend fun insertIntoTable(input: Products):Output
    suspend fun deleteProductById(productId:String):Output
    suspend fun searchProduct(input:String):Any
    suspend fun fetchByCategory(input: String):Any
    suspend fun updateProduct(input:UpdateProperties):Output


}