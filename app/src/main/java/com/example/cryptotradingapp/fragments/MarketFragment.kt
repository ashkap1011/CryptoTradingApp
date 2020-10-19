package com.example.cryptotradingapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptotradingapp.R
import com.example.cryptotradingapp.adapters.MarketCoinAdapter
import com.example.cryptotradingapp.adapters.OpenTradeAdapter
import com.example.cryptotradingapp.adapters.WalletAdapter
import com.example.cryptotradingapp.databinding.FragmentMarketBinding
import com.example.cryptotradingapp.viewmodels.LoginViewModel
import com.example.cryptotradingapp.viewmodels.MarketViewModel
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import org.json.JSONException
import org.json.JSONObject

class MarketFragment : Fragment(),MarketCoinAdapter.OnItemClickListener {

    private lateinit var binding: FragmentMarketBinding
    private lateinit var viewModel: MarketViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_market, container, false
        )

        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(requireActivity(),ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(
            MarketViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.startConnection()

        viewModel.retrieveOpenOrders()

        val adapterMarketCoin = MarketCoinAdapter(this)
        binding.marketCoinsList.adapter = adapterMarketCoin

        viewModel.marketDataFeed.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapterMarketCoin.data = it
            }
        })

        val adapterOpenTrade = OpenTradeAdapter(this)
        binding.openTradeList.adapter = adapterOpenTrade

        viewModel.openTrades.observe(viewLifecycleOwner, Observer{
            it?.let{
                adapterOpenTrade.data = it
            }
        })
        return binding.root
    }


    override fun onItemClick(position: Int) {
        Toast.makeText(context, "Item $position", Toast.LENGTH_SHORT).show()
        viewModel.selectedTradingPair(position)
        activity!!.supportFragmentManager.beginTransaction().apply{
            replace(R.id.fl_wrapper, MarketChartFragment())
            addToBackStack(null)
            commit()
        }
        //todo get item symbol, pass it to webview

    }

}