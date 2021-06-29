package com.lms.weatherapp.network

interface RepositoryCallback<T, E> {
    fun onSuccess(t: T)
    fun onLoading()
    fun onError(e: E)
}