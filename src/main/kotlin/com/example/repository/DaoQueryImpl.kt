package com.example.repository

import com.example.dao.DatabaseFactory
import com.example.dao.PropertiesDao
import com.example.data.request.UpdateProperties
import com.example.data.response.Properties
import com.example.data.response.Output
import com.example.data.response.OutputList
import com.example.exception.ProductNotFoundException
import io.ktor.http.*
import io.ktor.server.plugins.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.lang.NumberFormatException
import java.util.InputMismatchException


class DaoQueryImpl : DaoQuery {

    override suspend fun insertIntoTable(input: Products): Output {
        return try {
            DatabaseFactory.dbQuery {
                input.products.map { inList ->
                    PropertiesDao.insert {
                        it[id] = inList.id
                        it[title] = inList.title
                        it[description] = inList.description
                        it[price] = inList.price
                        it[discountPercentage] = inList.discountPercentage
                        it[rating] = inList.rating
                        it[stock] = inList.stock
                        it[brand] = inList.brand
                        it[category] = inList.category
                        it[thumbnail] = inList.thumbnail
                        it[images] = (inList.images).toString()
                    }
                }
            }
            Output("Data Has Inserted ",HttpStatusCode.Accepted.toString())
        }catch (e:Exception){
            when(e){
                is ExposedSQLException -> Output("Insertion Of Data Failed Due To : ${e.cause} ",HttpStatusCode.FailedDependency.toString())
                else -> {Output("System Error",HttpStatusCode.allStatusCodes.toString())}
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
                is ExposedSQLException -> Output("Error While Processing In DataBase",HttpStatusCode.BadRequest.toString())
                else -> {Output("System Error",HttpStatusCode.allStatusCodes.toString())}
            }
        }
    }

    override suspend fun searchProduct(input: String):Any {
        return try {
            val products =DatabaseFactory.dbQuery {
                PropertiesDao.selectAll().map { resultRowToProperties(it) }.filter { (it.title.equals(input, true)) || it.description.contains(input, true) }
            }
            if (products.isNotEmpty()){
                OutputList(products,HttpStatusCode.Accepted.toString())
            }
            else
                throw ProductNotFoundException("No Product Matches With The Given Input")
        }catch (e:Exception){
            when(e){
                is ProductNotFoundException -> Output("$e ${e.msg}",HttpStatusCode.NotAcceptable.toString())
                is InputMismatchException -> Output("$e ",HttpStatusCode.NotAcceptable.toString())
                is ExposedSQLException -> Output("$e",HttpStatusCode.FailedDependency.toString())
                else -> Output("System Error",HttpStatusCode.allStatusCodes.toString())
            }
        }
    }

    override suspend fun fetchByCategory(input: String): Any  {
        return try {
            val queryData=DatabaseFactory.dbQuery {
                PropertiesDao.select(PropertiesDao.category eq input.lowercase()).map { resultRowToProperties(it) }
            }
            if (queryData.isNotEmpty()){
                OutputList(queryData,HttpStatusCode.Accepted.toString())
            }
            else
                throw ProductNotFoundException("No Product Matches With The Given Input Category")
        }catch (e:Exception){
            when(e){
                is ProductNotFoundException -> Output("$e ${e.msg}",HttpStatusCode.NotAcceptable.toString())
                is InputMismatchException -> Output("$e ",HttpStatusCode.NotAcceptable.toString())
                is ExposedSQLException -> Output("$e",HttpStatusCode.FailedDependency.toString())
                else -> Output("System Error",HttpStatusCode.allStatusCodes.toString())
            }
        }
    }

    override suspend fun updateProduct(input: UpdateProperties):Output {
        return try {
            val result=DatabaseFactory.dbQuery {
                PropertiesDao.update({ PropertiesDao.id eq input.id!! }) {
                    when{
                        input.title !== null -> it[title] = input.title
                        input.description !== null-> it[description] = input.description
                        input.price !== null -> it[price] = input.price
                        input.discountPercentage !== null -> it[discountPercentage] = input.discountPercentage
                        input.rating !== null -> it[rating] = input.rating
                        input.stock !== null -> it[stock] = input.stock
                        input.brand !== null -> it[brand] = input.brand
                        input.category !== null  ->it[category] = input.category
                        input.thumbnail !== null -> it[thumbnail] = input.thumbnail
                        input.images !== null -> it[images] = input.images.toString()

                    }
                }
            } > 0
            if (result){
                Output("${input.id} Has Updated",HttpStatusCode.Accepted.toString())
            }
            else{
                throw ProductNotFoundException("${input.id} Does Not Exists")
            }
        }catch (e:Exception){
            when(e) {
                is ProductNotFoundException -> Output("$e  ${e.msg} ",HttpStatusCode.NotAcceptable.toString())
                is InputMismatchException -> Output("$e ",HttpStatusCode.NotAcceptable.toString())
                is BadRequestException -> Output("$e ",HttpStatusCode.NotAcceptable.toString())
                is ExposedSQLException -> Output("$e",HttpStatusCode.FailedDependency.toString())
                else -> Output("System Error",HttpStatusCode.allStatusCodes.toString())
            }
        }
    }
    private fun resultRowToProperties(row: ResultRow): Properties {
        return  Properties(row[PropertiesDao.id], row[PropertiesDao.title], row[PropertiesDao.description], row[PropertiesDao.price],
            row[PropertiesDao.discountPercentage],row[PropertiesDao.rating],row[PropertiesDao.stock],
            row[PropertiesDao.brand],row[PropertiesDao.category],row[PropertiesDao.thumbnail],row[PropertiesDao.images].split(","))

    }

}


