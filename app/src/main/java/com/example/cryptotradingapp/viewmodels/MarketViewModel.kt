package com.example.cryptotradingapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.cryptotradingapp.network.ExchangeService
import com.example.cryptotradingapp.network.MarketCoin
import com.example.cryptotradingapp.network.RetrofitInstance
import com.example.cryptotradingapp.network.UserService
import com.example.cryptotradingapp.repository.ExchangeRepository
import com.example.cryptotradingapp.repository.UserRepository

import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

class MarketViewModel(app:Application): AndroidViewModel(app) {
    private var exchangeRepository: ExchangeRepository = ExchangeRepository(app)
    lateinit var marketDataFeed:LiveData<List<MarketCoin>>
    lateinit var selectedTradingPair: String

    fun startConnection(){
        Log.i("marketData", "starting connection from viewmodel")
        marketDataFeed = exchangeRepository.startConnection()
        Log.i("marketData", "PRINTINGMARKETDATAFEEDINVIEWMODEL" + marketDataFeed.value.toString())
    }

    //connection starts in main activity

    fun selectedTradingPair(position:Int){
       // val marketSnapShot = marketDataFeed.value!!
        selectedTradingPair = marketDataFeed.value!!.elementAt(position).symbol.replace("/","")
    }





}

