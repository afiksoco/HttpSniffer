package com.example.http_sniffer_lib

data class SniffedRequest(
    val url: String,
    val method: String,
    val requestHeaders: Map<String, String>,
    val requestBody: String?,
    val responseCode: Int,
    val responseHeaders: Map<String, String>,
    val responseBody: String?,
    val durationMs: Long,
    val timestamp: Long = System.currentTimeMillis()
)
