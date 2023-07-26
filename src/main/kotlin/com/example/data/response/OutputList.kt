package com.example.data.response

import kotlinx.serialization.Serializable

@Serializable
data class OutputList(val list:List<Properties>, val status:String)