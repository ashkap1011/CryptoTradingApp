package com.example.cryptotradingapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.cryptotradingapp.domain.OpenTrade
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
    private var userRepository: UserRepository = UserRepository(app)
    lateinit var marketDataFeed:LiveData<List<MarketCoin>>
    lateinit var selectedTradingPair: String
    var openTrades: LiveData<List<OpenTrade>> = exchangeRepository.getCachedOpenTrades()

    fun startConnection(){
        marketDataFeed = exchangeRepository.startConnection()
    }

    //connection starts in main activity

    fun selectedTradingPair(position:Int){
       // val marketSnapShot = marketDataFeed.value!!
        selectedTradingPair = marketDataFeed.value!!.elementAt(position).symbol.replace("/","")
    }

    fun retrieveOpenOrders(){
        viewModelScope.async {
            exchangeRepository.fetchOpenTrades(userRepository.getUserAuthHeader())
            openTrades = exchangeRepository.openTrades!!
        }

    }




}

