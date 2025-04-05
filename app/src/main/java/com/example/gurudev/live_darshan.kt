package com.example.gurudev

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [live_darshan.newInstance] factory method to
 * create an instance of this fragment.
 */
class live_darshan : Fragment() {
private lateinit var live: WebView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_live_darshan, container, false)

        // Initialize the WebView
        live = view.findViewById(R.id.live)

        // Set up WebView settings
        live.webViewClient = WebViewClient()
        live.webChromeClient = WebChromeClient()
        val webSettings: WebSettings = live.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true

        // Load the YouTube video
        val videoUrl = "https://www.youtube.com/@shantikunjvideo/streams"
        live.loadUrl(videoUrl)

        return view
    }


}