package com.example.repository

import com.example.dao.*
import com.example.data.response.Properties
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class ContactsImpl : Contacts {
    override suspend fun storeContacts(input: ContactsStore): Unit =DatabaseFactory.dbQuery{
        UserTable.select(UserTable.name eq input.user)



        UserTable.insert { it[name]= input.user }
        ContactTable.insert {
            it[user]= input.user
            it[firstName]=input.firstName
            it[lastName]=input.lastName
            it[mobileNumber]=input.mobileNumber
            it[type]=input.type
        }

    }

    override suspend fun displayUserContacts(userName: String): List<ContactsDisplay> = DatabaseFactory.dbQuery {
        ContactTable.select(ContactTable.user eq userName).map { resultRowToContacts(it) }
    }
}

private fun resultRowToContacts(row: ResultRow): ContactsDisplay {
    return  ContactsDisplay(row[ContactTable.firstName], row[ContactTable.lastName], row[ContactTable.mobileNumber], row[ContactTable.type])

}