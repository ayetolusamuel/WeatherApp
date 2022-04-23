package com.pedek.weatherapp.core.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pedek.weatherapp.model.ErrorMessage
import okhttp3.ResponseBody
import java.text.ParseException
import java.text.SimpleDateFormat

fun Activity.isInternetAvailable(): Boolean {
    try {

        with(getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //Device is running on Marshmallow or later Android OS.
                with(getNetworkCapabilities(activeNetwork)) {
                    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
                    return this!!.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || hasTransport(
                        NetworkCapabilities.TRANSPORT_CELLULAR
                    )
                }
            } else {
                @Suppress("DEPRECATION")
                activeNetworkInfo?.let {
                    // connected to the internet
                    @Suppress("DEPRECATION")
                    return listOf(
                        ConnectivityManager.TYPE_WIFI,
                        ConnectivityManager.TYPE_MOBILE
                    ).contains(it.type)
                }
            }
        }
        return false
    } catch (exc: NullPointerException) {
        return false
    }
}

fun handleErrorMessage(res: ResponseBody): ErrorMessage {

    var errorMessage = ErrorMessage()
    val gson = Gson()
    val type = object : TypeToken<ErrorMessage>() {}.type
    val errorResponse: ErrorMessage? = gson.fromJson(res.charStream(), type)
    if (errorResponse != null) {
        errorMessage = errorResponse
    }
    return errorMessage
}

@SuppressLint("SimpleDateFormat")
fun convertLongDateToWord(longDate: Long): String {
    val spf = SimpleDateFormat("EEE, MMM yyyy  ")
    return spf.format(longDate)

}

@SuppressLint("SimpleDateFormat")
fun convertStringDateToReturnDay(dateString: String): String {
    val fromUser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val myFormat = SimpleDateFormat("EEE")
    try {
        return myFormat.format(fromUser.parse(dateString))
    } catch (e: ParseException) {
        e.printStackTrace()
        return ""
    }
}