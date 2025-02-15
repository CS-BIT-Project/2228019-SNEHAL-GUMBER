package com.example.gurudev

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment

class WebViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_web_view, container, false)
        val webView: WebView = view.findViewById(R.id.thesis)

        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true  // Enable JavaScript

        webView.webViewClient = WebViewClient()

        // Get URL from arguments
        val url = arguments?.getString("URL")
        url?.let { webView.loadUrl(it) }

        return view

    }

    companion object {
        fun newInstance(url: String): WebViewFragment {
            val fragment = WebViewFragment()
            val args = Bundle()
            args.putString("URL", url)
            fragment.arguments = args
            return fragment
        }
    }
}
