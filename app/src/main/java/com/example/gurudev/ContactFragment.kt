package com.example.gurudev

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment

class ContactFragment : Fragment() {
    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_contact, container, false)
        webView = view.findViewById(R.id.contact)

        // Enable JavaScript & DOM storage
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
        }

        // Set WebViewClient & WebChromeClient
        webView.webViewClient = object : WebViewClient() {}
        webView.webChromeClient = WebChromeClient()

        // Load URL
        webView.loadUrl("https://www.awgp.org/en/contact_us")

        return view
    }
}
