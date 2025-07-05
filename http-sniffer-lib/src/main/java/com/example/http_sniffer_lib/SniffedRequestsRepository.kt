package com.example.http_sniffer_lib

object SniffedRequestsRepository {

    private val requests = mutableListOf<SniffedRequest>()

    fun addRequest(request: SniffedRequest) {
        synchronized(requests) {
            requests.add(0, request)
        }
    }

    fun getAllRequests(): List<SniffedRequest> {
        return synchronized(requests) { requests.toList() }
    }

    fun clear() {
        synchronized(requests) { requests.clear() }
    }
}
