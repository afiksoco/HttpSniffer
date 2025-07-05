package com.example.http_sniffer_lib

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.http_sniffer_lib.databinding.ItemSnifferRequestBinding

class SnifferAdapter(
    private val requests: List<SniffedRequest>
) : RecyclerView.Adapter<SnifferAdapter.SnifferViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnifferViewHolder {
        val binding = ItemSnifferRequestBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SnifferViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SnifferViewHolder, position: Int) {
        val request = requests[position]
        holder.bind(request)
    }

    override fun getItemCount() = requests.size

    class SnifferViewHolder(private val binding: ItemSnifferRequestBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(request: SniffedRequest) {
            binding.urlText.text = request.url
            binding.methodText.text = request.method
            binding.statusText.text = "Status: ${request.responseCode}"

            // הוסף listener לפתיחת dialog עם body
            binding.root.setOnClickListener {
                AlertDialog.Builder(binding.root.context)
                    .setTitle("Response Body")
                    .setMessage(request.responseBody ?: "No body")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

    }
}
