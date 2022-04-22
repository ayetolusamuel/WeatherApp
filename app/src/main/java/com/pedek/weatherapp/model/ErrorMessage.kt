package com.pedek.weatherapp.model

data class ErrorMessage(
    var message: String = ""
){
    constructor():this("")
}