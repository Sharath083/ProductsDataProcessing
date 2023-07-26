package com.example.dao


import org.jetbrains.exposed.sql.Table


object PropertiesDao : Table("details_table") {

    val id = integer("id")
    val title = varchar("title", 128)
    val description = varchar("description", 1024)
    val price=integer("price")
    val discountPercentage=double("discountPercentage")
    val rating=double("rating")
    val stock=integer("stock")
    val brand=varchar("brand",45)
    val category=varchar("category",45)
    var thumbnail=varchar("thumbnail",100)
    var images= text("images")
    override val primaryKey = PrimaryKey(id)

}