package com.lms.weatherapp.location.factory



interface LocationFactory {
    fun buildLocation(callback: Callback, location: String)

    interface Callback {
        fun onSuccess(any:Any)
        fun onError(e: String)
        fun onLoading(isLoading: Boolean)
    }
}