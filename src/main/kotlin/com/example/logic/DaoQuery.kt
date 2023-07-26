package com.example.logic

import com.example.data.request.UpdateProperties
import com.example.data.response.Properties
import com.example.data.response.Output
import com.example.data.response.OutputList

interface DaoQuery {

    suspend fun insertIntoTable(input: Products):Output
    suspend fun deleteProductById(productId:String):Output
    suspend fun searchProduct(input:String):Any
    suspend fun fetchByCategory(input: String):Any
    suspend fun updateProduct(input:UpdateProperties):Output


}