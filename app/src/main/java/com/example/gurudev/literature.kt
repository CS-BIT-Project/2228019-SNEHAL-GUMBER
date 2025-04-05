package com.example.gurudev

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import com.example.gurudev.R.id.container


class LiteratureFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_literature, container, false)

        val spiritualButton: ImageButton = view.findViewById(R.id.spiritual)
        val fulfillButton: ImageButton = view.findViewById(R.id.fulfill)
        val womenButton: ImageButton = view.findViewById(R.id.women)
        val cognitionButton: ImageButton = view.findViewById(R.id.cognition)
        val awakeningButton: ImageButton = view.findViewById(R.id.awakening)
        val scienceButton: ImageButton = view.findViewById(R.id.science)
        val audioButton: ImageButton = view.findViewById(R.id.audio1)
        val audioButton2: ImageButton = view.findViewById(R.id.audio2)



        spiritualButton.setOnClickListener { openWebView("https://vicharkrantibooks.org/productlist?category_name=%E0%A4%B8%E0%A4%AE%E0%A4%BE%E0%A4%9C%20%E0%A4%A8%E0%A4%BF%E0%A4%B0%E0%A5%8D%E0%A4%AE%E0%A4%BE%E0%A4%A3%20|%20WELL-CULTURED%20&%20PROGRESSIVE%20SOCIETY&parent_category_id=24") }
        fulfillButton.setOnClickListener { openWebView("https://vicharkrantibooks.org/productlist?category_name=%E0%A4%B5%E0%A5%8D%E0%A4%AF%E0%A4%95%E0%A5%8D%E0%A4%A4%E0%A4%BF%20%E0%A4%A8%E0%A4%BF%E0%A4%B0%E0%A5%8D%E0%A4%AE%E0%A4%BE%E0%A4%A3%20|%20HUMAN%20EXCELLENCE%20YOUTH%20&%20STUDENTS&parent_category_id=10") }
        womenButton.setOnClickListener { openWebView("https://vicharkrantibooks.org/productlist?category_name=%E0%A4%B8%E0%A4%AE%E0%A4%BE%E0%A4%9C%20%E0%A4%A8%E0%A4%BF%E0%A4%B0%E0%A5%8D%E0%A4%AE%E0%A4%BE%E0%A4%A3%20|%20WELL-CULTURED%20&%20PROGRESSIVE%20SOCIETY&parent_category_id=24") }
        cognitionButton.setOnClickListener { openWebView("https://vicharkrantibooks.org/productlist?category_name=%E0%A4%B5%E0%A5%88%E0%A4%9C%E0%A5%8D%E0%A4%9E%E0%A4%BE%E0%A4%A8%E0%A4%BF%E0%A4%95%20%E0%A4%85%E0%A4%A7%E0%A5%8D%E0%A4%AF%E0%A4%BE%E0%A4%A4%E0%A5%8D%E0%A4%AE%E0%A4%B5%E0%A4%BE%E0%A4%A6%20|%20SCIENTIFIC%20SPIRITUALITY&parent_category_id=59") }
        awakeningButton.setOnClickListener { openWebView("https://vicharkrantibooks.org/productlist?category_name=%E0%A4%AF%E0%A5%81%E0%A4%97%20%E0%A4%AA%E0%A4%B0%E0%A4%BF%E0%A4%B5%E0%A4%B0%E0%A5%8D%E0%A4%A4%E0%A4%A8-%E0%A4%B5%E0%A4%BF%E0%A4%9A%E0%A4%BE%E0%A4%B0%20%E0%A4%95%E0%A5%8D%E0%A4%B0%E0%A4%BE%E0%A4%82%E0%A4%A4%E0%A4%BF%20|%20THOUGHT%20REVOLUTION&parent_category_id=80")}
        scienceButton.setOnClickListener { openWebView("https://vicharkrantibooks.org/productlist?category_name=%E0%A4%B5%E0%A5%88%E0%A4%9C%E0%A5%8D%E0%A4%9E%E0%A4%BE%E0%A4%A8%E0%A4%BF%E0%A4%95%20%E0%A4%85%E0%A4%A7%E0%A5%8D%E0%A4%AF%E0%A4%BE%E0%A4%A4%E0%A5%8D%E0%A4%AE%E0%A4%B5%E0%A4%BE%E0%A4%A6%20|%20SCIENTIFIC%20SPIRITUALITY&parent_category_id=59")}
        audioButton.setOnClickListener { openWebView("https://www.youtube.com/playlist?list=PLul20pCyQ_JG2cvFwHC6-Xk4NQUjfVOe_")}
        audioButton2.setOnClickListener { openWebView("https://www.youtube.com/playlist?list=PLul20pCyQ_JEW0uUg7MJ-Tn6qoSm7wGQl")}
        return view
    }

    private fun openWebView(url: String) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(container, WebViewFragment.newInstance(url))
            addToBackStack(null)  // Allows back navigation
            commit()
        }
    }
    }



