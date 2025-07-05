package com.example.http_sniffer_lib

interface SnifferCallback {
    fun onRequestSniffed(request: SniffedRequest)

}