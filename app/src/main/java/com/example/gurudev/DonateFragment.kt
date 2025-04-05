package com.example.gurudev

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient


class DonateFragment : Fragment() {
private lateinit var webView: WebView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     val view = inflater.inflate(R.layout.fragment_donate, container, false)
        webView = view.findViewById(R.id.donate)
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

                view?.loadUrl(url ?: "")
                return true
            }
        }
webView.loadUrl("https://www.awgp.org/en/contribute")
        return view
    }
}