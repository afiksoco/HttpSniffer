package com.example.http_sniffer_lib

import okhttp3.Interceptor
import okhttp3.Response

class HttpSnifferInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val startNs = System.nanoTime()

        val response = chain.proceed(request)

        val tookMs = (System.nanoTime() - startNs) / 1_000_000
        val responseBodyString = response.peekBody(Long.MAX_VALUE).string()

        val sniffedRequest = SniffedRequest(
            url = request.url.toString(),
            method = request.method,
            requestHeaders = request.headers.toMap(),
            requestBody = request.body?.toString(),
            responseCode = response.code,
            responseHeaders = response.headers.toMap(),
            responseBody = responseBodyString,
            durationMs = tookMs
        )

        // הוספה ל-Repository
        SniffedRequestsRepository.addRequest(sniffedRequest)
        HttpSniffer.notifyRequestSniffed(sniffedRequest)

        println("Sniffer: ${request.method} ${request.url} took ${tookMs}ms, response code ${response.code}")

        return response
    }
}

// פונקציה נוחה להמיר Headers למפה
private fun okhttp3.Headers.toMap(): Map<String, String> {
    val map = mutableMapOf<String, String>()
    for (name in names()) {
        map[name] = get(name) ?: ""
    }
    return map
}
