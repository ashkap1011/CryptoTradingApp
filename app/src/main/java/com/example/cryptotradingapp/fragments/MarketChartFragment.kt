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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MarketChartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MarketChartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentMarketChartBinding
    private lateinit var viewModel: MarketViewModel

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MarketChartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MarketChartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    class JSApplicationBridge(private val context: Context) {
        @JavascriptInterface
        fun onIntervalChanged(newInterval: String) {
            val toast = Toast.makeText(context, "New chart widget interval is $newInterval", Toast.LENGTH_SHORT)
            toast.show()
        }
    }



}