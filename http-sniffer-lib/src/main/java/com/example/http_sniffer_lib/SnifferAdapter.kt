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
                val context = binding.root.context
                val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_sniffer_response, null)
                val prettyCheckBox = dialogView.findViewById<CheckBox>(R.id.prettyCheckBox)
                val responseText = dialogView.findViewById<TextView>(R.id.responseText)

                // הצג כברירת מחדל את הטקסט בפורמט יפה
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

        private fun formatJson(json: String): String {
            return try {
                val trimmed = json.trim()
                if (trimmed.startsWith("{")) {
                    val jsonObject = JSONObject(trimmed)
                    jsonObject.toString(4)
                } else if (trimmed.startsWith("[")) {
                    val jsonArray = JSONArray(trimmed)
                    jsonArray.toString(4)
                } else {
                    json
                }
            } catch (e: Exception) {
                json
            }
        }

    }
}
