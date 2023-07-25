package com.example.logic

import com.example.dao.DatabaseFactory
import com.example.dao.PropertiesDao
import com.example.data.request.Properties
import com.example.data.response.Output
import com.example.exception.ProductNotFoundException
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import java.lang.NumberFormatException
import java.net.http.HttpClient
import java.sql.SQLException


class DaoQueryImpl : DaoQuery {
    override suspend fun insertIntoTable(input: Products): Unit = DatabaseFactory.dbQuery {
        input.products.map { inList->
            PropertiesDao.insert {
                it[id]=inList.id
                it[title]=inList.title
                it[body]=inList.description
                it[price]=inList.price
                it[discountPercentage]=inList.discountPercentage
                it[rating]=inList.rating
                it[stock]=inList.stock
                it[brand]=inList.brand
                it[category]=inList.category
                it[thumbnail]=inList.thumbnail
                it[images]= Json.encodeToString(inList.images)
            }
        }

    }

    override suspend fun deleteProductById(productId: String):Output {
        return try {
            DatabaseFactory.dbQuery{

                val response=PropertiesDao.deleteWhere { id eq productId.toInt()} > 0

                if (response) {
                    Output("$productId Has Deleted From Table","Success")
                }
                else{
                    throw ProductNotFoundException("Product Does Not Exists With Id $productId")
                }
            }
        }catch (e:Exception){
            when(e){
                is NumberFormatException -> Output("$e Enter Valid Input",HttpStatusCode.NotAcceptable.toString())
                is ProductNotFoundException -> Output("$e ${e.msg} ",HttpStatusCode.NotFound.toString())
                is SQLException -> Output("Error While Processing In DataBase",HttpStatusCode.BadRequest.toString())
                else -> {Output("System Error",HttpStatusCode.allStatusCodes.toString())}
            }
        }
    }


    private fun resultRowToFlight(row: ResultRow): Properties {
        return  Properties(row[PropertiesDao.id], row[PropertiesDao.title], row[PropertiesDao.body], row[PropertiesDao.price],
            row[PropertiesDao.discountPercentage],row[PropertiesDao.rating],row[PropertiesDao.stock],
            row[PropertiesDao.brand],row[PropertiesDao.category],row[PropertiesDao.thumbnail],row[PropertiesDao.images].split(","))

    }

}


