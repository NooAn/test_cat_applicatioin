package com.cat.bit.catapp.network

import com.cat.bit.catapp.API_HEADER
import com.cat.bit.catapp.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        builder.header(API_HEADER, API_KEY)
        return chain.proceed(builder.build())
    }
}