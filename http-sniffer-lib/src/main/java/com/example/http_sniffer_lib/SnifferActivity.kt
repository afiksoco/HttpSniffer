package com.example.http_sniffer_lib

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.http_sniffer_lib.databinding.ActivitySnifferBinding

class SnifferActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySnifferBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySnifferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.snifferToolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "HTTP Sniffer"
        }

        binding.snifferRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.snifferRecyclerView.adapter = SnifferAdapter(SniffedRequestsRepository.getAllRequests())
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

}
