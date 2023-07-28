package com.example.dao


import org.jetbrains.exposed.sql.Table

object UserTable:Table("userTable"){
    val name=varchar("name",24)
    override val primaryKey = PrimaryKey(name)
}
object ContactTable:Table("contactTable"){
    val user=reference("user",UserTable.name)
    val firstName=varchar("firstName",24)
    val lastName=varchar("lastName",24)
    val mobileNumber=varchar("mobileNumber",12)
    val type=varchar("type",6)
    override val primaryKey = PrimaryKey(mobileNumber)

}
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
    val thumbnail=varchar("thumbnail",100)
    val images= text("images")
    override val primaryKey = PrimaryKey(id)

}