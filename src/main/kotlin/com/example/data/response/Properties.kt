package com.example.data.response

import kotlinx.serialization.Serializable


@Serializable
data class Properties(
    var id:Int,
    var title:String,
    var description:String,
    var price:Int,
    var discountPercentage:Double,
    var rating:Double,
    var stock:Int,
    var brand:String,
    var category:String,
    var thumbnail:String,
    var images: List<String>
)