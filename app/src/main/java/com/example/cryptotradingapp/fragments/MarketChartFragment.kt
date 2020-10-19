package com.example.cryptotradingapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cryptotradingapp.R
import com.example.cryptotradingapp.databinding.FragmentMarketBinding
import com.example.cryptotradingapp.databinding.FragmentMarketChartBinding
import com.example.cryptotradingapp.viewmodels.MarketViewModel


class MarketChartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentMarketChartBinding
    private lateinit var viewModel: MarketViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_market_chart, container, false
        )

        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(
            MarketViewModel::class.java)

        binding.viewModel = viewModel

        initWebView()
        return binding.root

    }


    private fun initWebView() {
        val webView = binding.webView as WebView
        webView.settings.javaScriptEnabled = true
        webView.settings.allowFileAccessFromFileURLs = true

        // to prevent scaling text
        // see https://github.com/tradingview/charting_library/issues/3267#issuecomment-415031298
        webView.settings.textZoom = 100
        Log.i("MARKETTRADING", "LOOKING FOR SELECTED TRADINGPAIR-----------------------" +  viewModel.selectedTradingPair)

        val chartingLibraryUrl = "http://10.0.2.2:5005/marketChart/" + viewModel.selectedTradingPair
        val jsBridge = JSApplicationBridge(context!!)
        webView.addJavascriptInterface(jsBridge, "ApplicationBridge")

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                if (!url.equals(chartingLibraryUrl)) {
                    return
                }

                webView.evaluateJavascript("""
                    tvWidget.onChartReady(function() {
                        tvWidget.chart().onIntervalChanged().subscribe(
                            null,
                            function(interval) {
                                ApplicationBridge.onIntervalChanged(interval);
                            }
                        );
                    });
                """) {
                    // do nothing
                }
            }
        }

        // uncomment next line if you want to debug WebView in Chrome DevTools
        // WebView.setWebContentsDebuggingEnabled(true)

        webView.loadUrl(chartingLibraryUrl)
        //setContentView(webView)
    }

    class JSApplicationBridge(private val context: Context) {
        @JavascriptInterface
        fun onIntervalChanged(newInterval: String) {
            val toast = Toast.makeText(context, "New chart widget interval is $newInterval", Toast.LENGTH_SHORT)
            toast.show()
        }
    }



}