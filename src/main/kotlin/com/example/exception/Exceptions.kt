package com.example.exception

class ProductNotFoundException(val msg:String):RuntimeException()
class InvalidRequestType(val msg: String):RuntimeException()