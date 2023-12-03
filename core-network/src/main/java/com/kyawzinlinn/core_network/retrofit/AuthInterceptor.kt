package com.kyawzinlinn.core_network.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val modifiedUrl = originalRequest
            .url
            .newBuilder()
            .addQueryParameter("key",BuildConfig.API_KEY)
            .addQueryParameter("days",BuildConfig.DAYS)
            .build()

        val modifiedRequest = originalRequest.newBuilder()
            .url(modifiedUrl)
            .build()
        return chain.proceed(modifiedRequest)
    }
}