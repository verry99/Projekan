package com.test.test.presentation.dashboard.check_dpt

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.test.test.databinding.FragmentCheckDptBinding

class CheckDPTFragment : Fragment(), View.OnKeyListener {

    private var _binding: FragmentCheckDptBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckDptBinding.inflate(inflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpWebView()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() {
        binding.webView.apply {
            webViewClient = object : WebViewClient() {
                @Deprecated("Deprecated in Java")
                override fun onReceivedError(
                    view: WebView?,
                    errorCode: Int,
                    description: String?,
                    failingUrl: String?
                ) {
                    super.onReceivedError(view, errorCode, description, failingUrl)

                    binding.webView.visibility = View.GONE
                    binding.tvError.visibility = View.VISIBLE
                }
            }
            loadUrl("https://cekdptonline.kpu.go.id/")
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true

            setOnKeyListener(this@CheckDPTFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        val webView = binding.webView
        if (event?.action == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    requireActivity().onBackPressed()
                }
            }
        }
        return true
    }
}