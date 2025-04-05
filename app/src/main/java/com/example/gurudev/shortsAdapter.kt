package com.example.gurudev

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.recyclerview.widget.RecyclerView

class ShortsAdapter(private val shortsList: List<String>) : RecyclerView.Adapter<ShortsAdapter.ShortsViewHolder>() {

    class ShortsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val webView: WebView = itemView.findViewById(R.id.webviewShort)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_short, parent, false)
        return ShortsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShortsViewHolder, position: Int) {
        val videoId = shortsList[position]
        holder.webView.webChromeClient = WebChromeClient()
        holder.webView.webViewClient = WebViewClient()
        holder.webView.settings.javaScriptEnabled = true

        val videoUrl = "https://www.youtube.com/embed/$videoId?autoplay=1&loop=1"
        holder.webView.loadUrl(videoUrl)
    }

    override fun getItemCount(): Int = shortsList.size
}
