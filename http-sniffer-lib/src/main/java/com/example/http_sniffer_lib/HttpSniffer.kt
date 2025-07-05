package com.example.http_sniffer_lib

import android.content.Context
import android.content.Intent

object HttpSniffer {

    private var callback: SnifferCallback? = null

    fun showSnifferUI(context: Context) {
        val intent = Intent(context, SnifferActivity::class.java)
        context.startActivity(intent)
    }

    fun registerCallback(cb: SnifferCallback) {
        callback = cb
    }

    fun unregisterCallback() {
        callback = null
    }

    internal fun notifyRequestSniffed(request: SniffedRequest) {
        callback?.onRequestSniffed(request)
    }
}