package com.example.cryptotradingapp.repository

import android.app.Application
import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.cryptotradingapp.R
import com.example.cryptotradingapp.database.OpenTradesDao
import com.example.cryptotradingapp.database.OpenTradesDatabase
import com.example.cryptotradingapp.database.entities.asDomainModel
import com.example.cryptotradingapp.database.entities.asDomainModels
import com.example.cryptotradingapp.domain.LimitOrder
import com.example.cryptotradingapp.domain.MarketOrder
import com.example.cryptotradingapp.domain.OpenTrade
import com.example.cryptotradingapp.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ExchangeRepository(app: Application) {
    private val userService = RetrofitInstance.getRetrofitInstance().create(UserService::class.java)
    private val exchangeService = RetrofitInstance.getRetrofitInstance().create(ExchangeService::class.java)
    private val marketDataService = MarketDataService()
    private val application = app
    private val resources = application.resources
    private val openTradesDao = OpenTradesDatabase.getInstanceOpenTradesDB(app).openTradesDao

    lateinit var openTrades: LiveData<List<OpenTrade>>

    //todo maybe not the best way to instantiate open trades
    init{
    }

    suspend fun getCoinPrice(symbol:String): ResponseMessage{
       return exchangeService.getCoinPrice(symbol)
    }

    suspend fun fetchOpenTrades(authHeader: String){
            if(isUserLoggedIn()){
                val openTradesNetwork = exchangeService.getOpenTrades(authHeader)
                openTradesDao.insertAllOpenTradeItems(openTradesNetwork.asDatabaseModel())
                openTrades = Transformations.map(openTradesDao.getAllOpenTrades()) {
                    it.asDomainModel()
                }
            }
    }

    fun getCachedOpenTrades():LiveData<List<OpenTrade>>{
        openTrades = Transformations.map(openTradesDao.getAllOpenTrades()) {
            it.asDomainModel()
        }
        return openTrades
    }



    suspend fun placeMarketOrder(authHeader: String, order: MarketOrder): ResponseMessage{
        return exchangeService.placeMarketOrder(authHeader,order)
    }

    suspend fun placeLimitOrder(authHeader:String, order: LimitOrder): ResponseMessage{
        return exchangeService.placeLimitOrder(authHeader,order)
    }

    fun startConnection() : LiveData<List<MarketCoin>>{
        marketDataService.startConnection()
        return marketDataService.marketData
    }


    fun isUserLoggedIn():Boolean{
        var isLoggedIn = false

        val sharedPref = application.getSharedPreferences(resources.getString(R.string.user_session),Context.MODE_PRIVATE)
        if (sharedPref.contains(resources.getString(R.string.pref_key_login))){
            isLoggedIn = sharedPref.getBoolean(resources.getString(R.string.pref_key_login),false)
        }
        if(isLoggedIn) {
            return true
        }
        return false

    }




}