package com.example.http_sniffer_lib

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.http_sniffer_lib.databinding.ItemSnifferRequestBinding
import org.json.JSONArray
import org.json.JSONObject
class SnifferAdapter(
    private val requests: List<SniffedRequest>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_EMPTY = 0
    private val VIEW_TYPE_REQUEST = 1

    override fun getItemViewType(position: Int): Int {
        return if (requests.isEmpty()) VIEW_TYPE_EMPTY else VIEW_TYPE_REQUEST
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_EMPTY) {
            val emptyView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_sniffer_empty, parent, false)
            EmptyViewHolder(emptyView)
        } else {
            val binding = ItemSnifferRequestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            SnifferViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SnifferViewHolder && requests.isNotEmpty()) {
            holder.bind(requests[position])
        }
    }

    override fun getItemCount(): Int {
        return if (requests.isEmpty()) 1 else requests.size
    }

    class EmptyViewHolder(view: android.view.View) : RecyclerView.ViewHolder(view)

    class SnifferViewHolder(private val binding: ItemSnifferRequestBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(request: SniffedRequest) {
            binding.urlText.text = request.url
            binding.methodText.text = request.method

            binding.statusText.text = "Status: ${request.responseCode}"
            binding.statusText.setTextColor(getStatusColor(request.responseCode))

            binding.root.setOnClickListener {
                val context = binding.root.context
                val dialogView = LayoutInflater.from(context)
                    .inflate(R.layout.dialog_sniffer_response, null)
                val prettyCheckBox = dialogView.findViewById<CheckBox>(R.id.prettyCheckBox)
                val responseText = dialogView.findViewById<TextView>(R.id.responseText)

                responseText.text = formatJson(request.responseBody ?: "")

                val dialog = AlertDialog.Builder(context)
                    .setTitle("Response Body")
                    .setView(dialogView)
                    .setPositiveButton("OK", null)
                    .create()

                prettyCheckBox.setOnCheckedChangeListener { _, isChecked ->
                    responseText.text = if (isChecked) {
                        formatJson(request.responseBody ?: "")
                    } else {
                        request.responseBody ?: ""
                    }
                }

                dialog.show()
            }
        }

        private fun getStatusColor(code: Int?): Int {
            val context = binding.root.context
            return when {
                code == null -> context.getColor(android.R.color.darker_gray)
                code in 200..299 -> context.getColor(android.R.color.holo_green_dark)
                code in 300..399 -> context.getColor(android.R.color.holo_orange_dark)
                code >= 400 -> context.getColor(android.R.color.holo_red_dark)
                else -> context.getColor(android.R.color.darker_gray)
            }
        }

        private fun formatJson(json: String): String {
            return try {
                val trimmed = json.trim()
                if (trimmed.startsWith("{")) {
                    JSONObject(trimmed).toString(4)
                } else if (trimmed.startsWith("[")) {
                    JSONArray(trimmed).toString(4)
                } else {
                    json
                }
            } catch (e: Exception) {
                json
            }
        }
    }
}
