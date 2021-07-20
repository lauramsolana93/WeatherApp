package com.lms.weatherapp.location.factory

import com.lms.weatherapp.location.model.Location


interface LocationFactory {
    //fun buildLocation(callback: Callback, location: String)
    suspend fun buildLocation(location: String): Location

    interface Callback {
        fun onSuccess(any:Any)
        fun onError(e: String)
        fun onLoading(isLoading: Boolean)
    }
}