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

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CommunityFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var webView: WebView // Declare WebView for video
    private lateinit var webView2: WebView // Declare WebView for news

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_community, container, false)

        webView = view.findViewById(R.id.webviewVideo)
        webView2 = view.findViewById(R.id.webviewNews)

        // Set up WebView for video
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true

        // Set up WebView for news
        webView2.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                // Load the URL in the WebView instead of the default browser
                view?.loadUrl(url ?: "")
                return true
            }
        }
        webView2.webChromeClient = WebChromeClient()
        val webSettings2: WebSettings = webView2.settings
        webSettings2.javaScriptEnabled = true
        webSettings2.domStorageEnabled = true // Enable DOM storage if needed

        // Load URLs
        val videoId = "FXdLulEkp80"
        val videoUrl = "https://www.youtube.com/embed/$videoId?autoplay=1&rel=0"
        webView.loadUrl(videoUrl)
        webView2.loadUrl("https://www.awgp.org/en/news")

        return view
    }

    fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack() // Go back to the previous page in WebView
        } else if (webView2.canGoBack()) {
            webView2.goBack() // Go back to the previous page in webView2
        } else {
            // Handle back press in the activity
            requireActivity().onBackPressed() // Call the activity's onBackPressed
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CommunityFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}