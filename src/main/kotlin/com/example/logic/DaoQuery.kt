package com.example.logic

import com.example.data.response.Output

interface DaoQuery {

    suspend fun insertIntoTable(input: Products)
    suspend fun deleteProductById(productId:String):Output

}