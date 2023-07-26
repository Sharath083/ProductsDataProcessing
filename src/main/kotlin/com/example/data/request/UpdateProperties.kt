package com.example.data.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProperties(
    var id:Int?=null,
    var title: String? =null,
    var description:String? =null,
    var price:Int? =null,
    var discountPercentage:Double? =null,
    var rating:Double? =null,
    var stock:Int? =null,
    var brand:String? =null,
    var category:String? =null,
    var thumbnail:String? =null,
    var images: List<String>? =null
)