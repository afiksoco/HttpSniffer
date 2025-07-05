package com.example.httpsniffer

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.http_sniffer_lib.HttpSniffer
import com.example.http_sniffer_lib.HttpSnifferInterceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // נוספה טעינת ה-layout

        // בנה OkHttpClient עם ה-Interceptor שלך
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpSnifferInterceptor())
            .build()

        // שלח קריאה לבדיקה
        thread {
            val request = Request.Builder()
                .url("https://jsonplaceholder.typicode.com/todos/1")
                .build()

            val response = client.newCall(request).execute()
            val body = response.body?.string()
            println("MainActivity Response: $body")
        }

        // חבר את הכפתור לפתיחת ה-Sniffer
        val button = findViewById<Button>(R.id.openSnifferButton)
        button.setOnClickListener {
            HttpSniffer.showSnifferUI(this)
        }
    }
}
