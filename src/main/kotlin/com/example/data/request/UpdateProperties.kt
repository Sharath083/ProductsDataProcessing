package com.example.data.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProperties(
    val id:Int?=null,
    val title: String? =null,
    val description:String? =null,
    val price:Int? =null,
    val discountPercentage:Double? =null,
    val rating:Double? =null,
    val stock:Int? =null,
    val brand:String? =null,
    val category:String? =null,
    val thumbnail:String? =null,
    val images: List<String>? =null
)