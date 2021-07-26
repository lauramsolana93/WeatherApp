package com.lms.weatherapp.location.factory

import com.lms.weatherapp.common.network.model.location.GeopositionResponse
import com.lms.weatherapp.location.model.Location
import retrofit2.Response


interface LocationFactory {
    //fun buildLocation(callback: Callback, location: String)
    suspend fun buildLocation(location: String): Response<GeopositionResponse>

    interface Callback {
        fun onSuccess(any:Any)
        fun onError(e: String)
        fun onLoading(isLoading: Boolean)
    }
}